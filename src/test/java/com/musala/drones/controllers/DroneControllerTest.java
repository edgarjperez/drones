package com.musala.drones.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.drones.dto.DroneDto;
import com.musala.drones.dto.DroneWithMedicationsDto;
import com.musala.drones.dto.MedicationDto;
import com.musala.drones.model.Drone;
import com.musala.drones.model.Medication;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import com.musala.drones.service.DroneService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DroneController.class)
public class DroneControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DroneService service;

    @Test
    public void shouldRegisterADrone() throws Exception {
        DroneDto newDrone = initialDrone();
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/drones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newDrone))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldAddMedicationToADrone() throws Exception {
        var medication = new Medication(
                "Dummy-Medication_1",
                (int) (Math.random() * 500) + 1,
                "DUM_01",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/VariousPills.jpg/1600px-VariousPills.jpg?20070116123730"
        );
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(put("/drones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medication))
        ).andExpect(status().isOk());
    }



    private static DroneDto initialDrone() {
        var models = Model.values();
        return new DroneDto(
                UUID.randomUUID().toString(),
                models[new Random().nextInt(models.length - 1) + 1],
                500,
                (int) (Math.random() * 100) + 1,
                State.IDLE
        );
    }


}
