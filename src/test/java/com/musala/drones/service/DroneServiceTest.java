package com.musala.drones.service;

import com.musala.drones.dto.MedicationDto;
import com.musala.drones.exceptions.DroneBatteryLevelException;
import com.musala.drones.exceptions.DroneLoadException;
import com.musala.drones.model.Drone;
import com.musala.drones.model.Medication;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import com.musala.drones.repositories.DroneRepository;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DroneServiceTest {

    @Mock
    private DroneRepository repository;

    @InjectMocks
    private DroneService service;

    @Test
    void shouldThrowsDroneLoadExceptionWhenLoad() throws Exception {
        var drone = initialDroneModel();
        var medication = new MedicationDto(
                "Dummy-Medication_1",
                300,
                "DUM_01",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/VariousPills.jpg/1600px-VariousPills.jpg?20070116123730"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(drone));


        assertThrows(DroneLoadException.class, () -> {
            service.loadDrone(1L, medication);
        });
    }

    @Test
    void shouldThrowsDroneBatteryLevelExceptionWhenLoad() throws Exception {
        var drone = initialDroneModel();
        drone.setBatteryCapacity(15);
        var medication = new MedicationDto(
                "Dummy-Medication_1",
                100,
                "DUM_01",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/VariousPills.jpg/1600px-VariousPills.jpg?20070116123730"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(drone));


        assertThrows(DroneBatteryLevelException.class, () -> {
            service.loadDrone(1L, medication);
        });
    }

    private static Drone initialDroneModel() {
        var models = Model.values();
        return new Drone(
                UUID.randomUUID().toString(),
                models[new Random().nextInt(models.length - 1) + 1],
                500,
                100,
                State.IDLE,
                new ArrayList<>(Lists.list(new Medication(
                        "Dummy-Medication_1",
                        300,
                        "DUM_01",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/VariousPills.jpg/1600px-VariousPills.jpg?20070116123730"
                )))
        );
    }
}