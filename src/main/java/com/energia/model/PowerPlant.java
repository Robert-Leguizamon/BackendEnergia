package com.energia.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "power_plant", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "company_id" }))
public class PowerPlant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

 

  public PowerPlant() {
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

  public Company getCompany() {
    return this.company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Region getRegion() {
    return this.region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public EnergyType getEnergyType() {
    return this.energyType;
  }

  public void setEnergyType(EnergyType energyType) {
    this.energyType = energyType;
  }

  public List<EnergyRecord> getEnergyRecords() {
    return this.energyRecords;
  }

  public void setEnergyRecords(List<EnergyRecord> energyRecords) {
    this.energyRecords = energyRecords;
  }

  @ManyToOne(optional = false)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne(optional = false)
  @JoinColumn(name = "region_id", nullable = false)
  private Region region;

  @ManyToOne(optional = false)
  @JoinColumn(name = "energy_type_id", nullable = false)
  private EnergyType energyType;
  
  @JsonIgnore
  @OneToMany(mappedBy = "powerPlant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EnergyRecord> energyRecords;

}