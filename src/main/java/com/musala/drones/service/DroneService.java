package com.musala.drones.service;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.DroneWithMedicationsDto;
import com.musala.drones.dto.MedicationDto;
import com.musala.drones.model.Drone;
import com.musala.drones.repositories.DroneRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneService {

    private final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
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
}
