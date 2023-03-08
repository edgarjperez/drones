package com.musala.drones.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "drones")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "serial_number", length = 100)
    private String serialNumber;
    @Enumerated(value = EnumType.STRING)
    private Model model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "drone_id")
    private List<Medication> medications;

    public Drone() {
    }

    public Drone(String serialNumber,
                 Model model,
                 Integer weightLimit,
                 Integer batteryCapacity,
                 State state,
                 List<Medication> medications) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.medications = medications;
    }

    /**
     * Constructor method generated with the only purpose of
     * serve as factory of initial data for the project
     * @return a new {@link Drone} Object with dummy Data
     */
    public static Drone initialDrone() {
        var states = State.values();
        var models = Model.values();
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(new Medication(
                UUID.randomUUID().toString(),
                (int)(Math.random() * 500) + 1,
                "",
                ""
                )
        );
        return new Drone(
                UUID.randomUUID().toString(),
                models[new Random().nextInt(models.length - 1) + 1],
                (int)(Math.random() * 500) + 1,
                (int)(Math.random() * 100) + 1,
                states[new Random().nextInt(states.length - 1) + 1],
                medicationList
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Medication> getMedications() {
        return medications;
    }
}
