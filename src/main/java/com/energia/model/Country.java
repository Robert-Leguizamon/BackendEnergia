package com.energia.model;

import jakarta.persistence.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;//evita infinitos

@Entity
@Table(name = "country", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  public Country() {
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

  public List<Region> getRegions() {
    return this.regions;
  }

  public void setRegions(List<Region> regions) {
    this.regions = regions;
  }

  public List<Company> getCompanies() {
    return this.companies;
  }

  public void setCompanies(List<Company> companies) {
    this.companies = companies;
  }

  @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Region> regions;

  @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Company> companies;
}