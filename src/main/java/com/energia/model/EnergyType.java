package com.energia.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "energy_type", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class EnergyType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private Boolean renewable;
  @JsonIgnore
  @OneToMany(mappedBy = "energyType")
  private List<PowerPlant> powerPlants;

  public EnergyType() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean isRenewable() {
    return this.renewable;
  }

  public Boolean getRenewable() {
    return this.renewable;
  }

  public void setRenewable(Boolean renewable) {
    this.renewable = renewable;
  }

  public List<PowerPlant> getPowerPlants() {
    return this.powerPlants;
  }

  public void setPowerPlants(List<PowerPlant> powerPlants) {
    this.powerPlants = powerPlants;
  }

}