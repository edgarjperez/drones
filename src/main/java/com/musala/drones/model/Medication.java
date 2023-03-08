package com.musala.drones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;
    private Integer weight;
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;
    private String image;

    public Medication() {
    }

    public Medication(String name, Integer weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
