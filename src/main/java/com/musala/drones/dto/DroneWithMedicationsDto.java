package com.musala.drones.dto;

import com.musala.drones.model.Model;
import com.musala.drones.model.State;

import java.util.List;

public record DroneWithMedicationsDto(
        String serialNumber,
        Model model,
        Integer weight,
        Integer batteryCapacity,
        State state,
        List<MedicationDto> medications) {
}
