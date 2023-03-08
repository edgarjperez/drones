package com.musala.drones.dto;

import com.musala.drones.model.Model;
import com.musala.drones.model.State;

public record DroneDto(String serialNumber, Model model, Integer weight, Integer batteryCapacity, State state) {
}
