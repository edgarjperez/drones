package com.musala.drones.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "battery_level_audit")
public class DroneBatteryLevelHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String droneSerialNumber;
    private Integer batteryLevel;
    @Enumerated(value = EnumType.STRING)
    private State state;

    private Timestamp lastUpdated;

    public DroneBatteryLevelHistory() {
    }

    public DroneBatteryLevelHistory(String droneSerialNumber, Integer batteryLevel, State state) {
        this.droneSerialNumber = droneSerialNumber;
        this.batteryLevel = batteryLevel;
        this.state = state;
        this.lastUpdated = new Timestamp(new Date().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public void setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
