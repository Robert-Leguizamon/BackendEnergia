package com.energia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "region", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "country_id" }))
@Getter
@Setter
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  public Region() {
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

  public Country getCountry() {
    return this.country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public List<PowerPlant> getPowerPlants() {
    return this.powerPlants;
  }

  public void setPowerPlants(List<PowerPlant> powerPlants) {
    this.powerPlants = powerPlants;
  }

  @ManyToOne(optional = false)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;

  @JsonIgnore
  @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PowerPlant> powerPlants;
}
