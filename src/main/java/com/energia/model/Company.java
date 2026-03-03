package com.energia.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "company", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "country_id" }))
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  public Company() {
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

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public List<PowerPlant> getPowerPlants() {
    return powerPlants;
  }

  public void setPowerPlants(List<PowerPlant> powerPlants) {
    this.powerPlants = powerPlants;
  }

  @ManyToOne(optional = false)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<PowerPlant> powerPlants;

}
