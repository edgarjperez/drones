package com.musala.drones;

import com.musala.drones.model.Drone;
import com.musala.drones.repositories.DroneRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;
@EnableScheduling
@SpringBootApplication
public class DronesApplication {

    public static void main(String[] args) {
        var configurableApplicationContext =
                SpringApplication.run(DronesApplication.class, args);

        var droneRepository = configurableApplicationContext.getBean(DroneRepository.class);

        List<Drone> droneList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            droneList.add(Drone.initialDrone());
        }

        droneRepository.saveAll(droneList);

    }

}
