package com.musala.drones.model;

import com.musala.drones.dto.MedicationDto;
import com.musala.drones.exceptions.DroneBatteryLevelException;
import com.musala.drones.exceptions.DroneLoadException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

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

    public Drone(String serialNumber,
                 Model model,
                 Integer weightLimit,
                 Integer batteryCapacity,
                 State state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    /**
     * Constructor method generated with the only purpose of
     * serve as factory of initial data for the project
     *
     * @return a new {@link Drone} Object with dummy Data
     */
    public static Drone initialDrone() {
        var states = State.values();
        var models = Model.values();
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(new Medication(
                        "Dummy-Medication_1",
                        (int) (Math.random() * 500) + 1,
                        "DUM_01",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/VariousPills.jpg/1600px-VariousPills.jpg?20070116123730"
                )
        );
        return new Drone(
                UUID.randomUUID().toString(),
                models[new Random().nextInt(models.length - 1) + 1],
                500,
                (int) (Math.random() * 100) + 1,
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

    public Drone loadMedication(MedicationDto medicationDto) throws DroneLoadException {
        if (isOverWeight(medicationDto.weight()).test(this)) {
            throw new DroneLoadException("A drone cannot carry more that its recommended wight.");
        }
        if (isBatteryBellow25Percent().test(this)) {
            throw new DroneBatteryLevelException("The Drone doesn't have enough battery");
        }
        Medication medication = new Medication(
                medicationDto.name(),
                medicationDto.weight(),
                medicationDto.code(),
                medicationDto.image()
        );
        this.getMedications().add(medication);
        this.setState(State.LOADING);
        return this;
    }

    private Predicate<Drone> isOverWeight(int medicationWeight) {
        return drone -> this.getMedications().stream().mapToInt(Medication::getWeight).sum()
                + medicationWeight > drone.weightLimit;
    }

    private Predicate<Drone> isBatteryBellow25Percent() {
        return drone -> drone.batteryCapacity < 25;
    }
}
