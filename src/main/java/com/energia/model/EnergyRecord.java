package com.energia.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "energy_record", uniqueConstraints = @UniqueConstraint(columnNames = { "power_plant_id", "year", "month",
    "measurement_type_id" }))
public class EnergyRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false)
  private Integer month;

  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal value;

  @ManyToOne(optional = false)
  @JoinColumn(name = "power_plant_id", nullable = false)
  private PowerPlant powerPlant;

  @ManyToOne(optional = false)
  @JoinColumn(name = "measurement_type_id", nullable = false)
  private MeasurementType measurementType;

  public EnergyRecord() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getYear() {
    return this.year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return this.month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public BigDecimal getValue() {
    return this.value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public PowerPlant getPowerPlant() {
    return this.powerPlant;
  }

  public void setPowerPlant(PowerPlant powerPlant) {
    this.powerPlant = powerPlant;
  }

  public MeasurementType getMeasurementType() {
    return this.measurementType;
  }

  public void setMeasurementType(MeasurementType measurementType) {
    this.measurementType = measurementType;
  }

}