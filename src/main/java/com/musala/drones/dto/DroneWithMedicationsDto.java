package com.musala.drones.dto;

import com.musala.drones.model.Model;
import com.musala.drones.model.State;

import java.util.List;

public record DroneWithMedicationsDto(
        DroneDto droneDto,
        List<MedicationDto> medications) {
}
