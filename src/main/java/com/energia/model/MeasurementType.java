package com.energia.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "measurement_type", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class MeasurementType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private String unit;
  
  public MeasurementType() {
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

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public List<EnergyRecord> getEnergyRecords() {
    return this.energyRecords;
  }

  public void setEnergyRecords(List<EnergyRecord> energyRecords) {
    this.energyRecords = energyRecords;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "measurementType")
  private List<EnergyRecord> energyRecords;

}
