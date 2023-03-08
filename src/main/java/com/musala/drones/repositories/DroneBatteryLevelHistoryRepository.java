package com.musala.drones.repositories;

import com.musala.drones.model.DroneBatteryLevelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryLevelHistoryRepository extends JpaRepository<DroneBatteryLevelHistory, Long> {
}
