package com.musala.drones.repositories;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.DroneWithMedicationsDto;
import com.musala.drones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("""
            SELECT new com.musala.drones.dto.DroneDto(d.serialNumber, d.model, d.weightLimit, d.batteryCapacity, d.state)
            FROM Drone d
            """)
    List<DroneDto> getAllDrones();
    @Query("""
            SELECT new com.musala.drones.dto.DroneWithMedicationsDto(
            d.serialNumber, d.model, d.weightLimit, d.batteryCapacity, d.state, d.medications)
            FROM Drone d
            WHERE d.id=:id
            """)
    DroneWithMedicationsDto getADroneWithMedications(@Param("id") Long id);
}
