package com.musala.drones.controllers;


import com.musala.drones.dto.DroneBatteryLevelDto;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.DroneWithMedicationsDto;
import com.musala.drones.dto.MedicationDto;
import com.musala.drones.service.DroneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class DroneController {

    private final DroneService service;

    public DroneController(DroneService service) {
        this.service = service;
    }

    @GetMapping("/drones")
    public List<DroneDto> allDrones() {
        return this.service.allDrones();
    }

    @GetMapping("/drones/{id}")
    public DroneWithMedicationsDto allDrones(@PathVariable("id") Long id) {
        return this.service.getADrone(id);
    }

    @PostMapping("/drones")
    public DroneDto registerADrone(@RequestBody DroneDto drone) {
        return this.service.registerADrone(drone);
    }

    @PutMapping("/drones/{id}")
    public DroneWithMedicationsDto loadMedication(@PathVariable("id") Long droneId, @RequestBody MedicationDto medication) {
        return this.service.loadDrone(droneId, medication);
    }

    @GetMapping("/drones/available")
    public List<DroneDto> allAvailableDrones() {
        return this.service.getAvailableDrones();
    }
    @GetMapping("/drones/{id}/medications")
    public List<MedicationDto> checkMedications(@PathVariable("id") Long droneId) {
        return this.service.checkMedications(droneId);
    }

    @GetMapping("/drones/{id}/battery")
    public DroneBatteryLevelDto checkBattery(@PathVariable("id") Long droneId) {
        return this.service.getBatteryLevelForADrone(droneId);
    }

}
