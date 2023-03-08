package com.musala.drones.service;

import com.musala.drones.dto.DroneDto;
import com.musala.drones.model.DroneBatteryLevelHistory;
import com.musala.drones.repositories.DroneBatteryLevelHistoryRepository;
import com.musala.drones.repositories.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneBatteryLevelService {

    private final DroneBatteryLevelHistoryRepository repository;
    private final DroneRepository droneRepository;

    public DroneBatteryLevelService(DroneBatteryLevelHistoryRepository repository, DroneRepository droneRepository) {
        this.repository = repository;
        this.droneRepository = droneRepository;
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void checkBatteryLevels() {
        var drones = this.droneRepository.getAllDrones();
        drones.stream()
                .map(drone -> new DroneBatteryLevelHistory(drone.serialNumber(),drone.batteryCapacity(), drone.state()))
                .forEach(this.repository::save);
    }
}
