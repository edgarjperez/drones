package com.musala.drones.service;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.DroneWithMedicationsDto;
import com.musala.drones.dto.MedicationDto;
import com.musala.drones.model.Drone;
import com.musala.drones.model.Medication;
import com.musala.drones.repositories.DroneRepository;
import com.musala.drones.repositories.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DroneService(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Transactional(readOnly = true)
    public List<DroneDto> allDrones() {
        return this.droneRepository.getAllDrones();
    }

    @Transactional(readOnly = true)
    public DroneWithMedicationsDto getADrone(long id) {
        //TODO Use JPA projections, need further investigation
        return this.droneRepository.findById(id)
                .map(drone ->
                        new DroneWithMedicationsDto(
                                drone.getSerialNumber(),
                                drone.getModel(),
                                drone.getWeightLimit(),
                                drone.getBatteryCapacity(),
                                drone.getState(),
                                drone.getMedications()
                                        .stream()
                                        .map(medication ->
                                                new MedicationDto(
                                                        medication.getName(),
                                                        medication.getWeight(),
                                                        medication.getCode(),
                                                        medication.getImage())
                                        ).collect(Collectors.toList()))
                ).orElseThrow(InternalError::new);
    }

    public DroneDto registerADrone(DroneDto droneDto) {
        return Optional.of(this.droneRepository.save(new Drone(
                        droneDto.serialNumber(),
                        droneDto.model(),
                        droneDto.weight(),
                        droneDto.batteryCapacity(),
                        droneDto.state())))
                .map(savedDrone -> new DroneDto(
                        savedDrone.getSerialNumber(),
                        savedDrone.getModel(),
                        savedDrone.getWeightLimit(),
                        savedDrone.getBatteryCapacity(),
                        savedDrone.getState()))
                .orElseThrow(InternalError::new);
    }

    public DroneWithMedicationsDto loadDrone(Long droneId, MedicationDto medicationDto) {
        return this.droneRepository.findById(droneId)
                .map(drone -> drone.loadMedication(medicationDto))
                .map(this.droneRepository::saveAndFlush)
                .map(updatedDrone -> new DroneWithMedicationsDto(
                        updatedDrone.getSerialNumber(),
                        updatedDrone.getModel(),
                        updatedDrone.getWeightLimit(),
                        updatedDrone.getBatteryCapacity(),
                        updatedDrone.getState(),
                        updatedDrone.getMedications()
                                .stream()
                                .map(medication ->
                                        new MedicationDto(
                                                medication.getName(),
                                                medication.getWeight(),
                                                medication.getCode(),
                                                medication.getImage())
                                ).collect(Collectors.toList()))
                ).orElseThrow(InternalError::new);

    }

    @Transactional(readOnly = true)
    public List<DroneDto> getAvailableDrones() {
        return this.droneRepository.findAll().stream()
                .filter(drone -> drone.getMedications().stream().mapToInt(Medication::getWeight).sum() < 500)
                .map(drone -> new DroneDto(drone.getSerialNumber(),
                        drone.getModel(),
                        drone.getWeightLimit(),
                        drone.getBatteryCapacity(),
                        drone.getState()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicationDto> checkMedications(long id) {
        //TODO Use JPA projections, need further investigation
        return this.droneRepository.findById(id).stream()
                .map(Drone::getMedications)
                .flatMap(medication -> medication.stream().map(medication1 ->
                        new MedicationDto(
                                medication1.getName(),
                                medication1.getWeight(),
                                medication1.getCode(),
                                medication1.getImage()))
                ).toList();
    }
}
