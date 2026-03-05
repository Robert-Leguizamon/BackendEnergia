package com.energia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.PowerPlant;
import com.energia.service.PowerPlantService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/powerplants")
@RequiredArgsConstructor
public class PowerPlantController {
  private final PowerPlantService powerPlantService;

  @PostMapping
  public PowerPlant create(@RequestBody PowerPlant powerPlant) {
    return powerPlantService.save(powerPlant);
  }

  @GetMapping
  public List<PowerPlant> findAll() {
    return powerPlantService.findAll();
  }

  @GetMapping("/{id}")
  public PowerPlant findById(@PathVariable Long id) {
    return powerPlantService.findById(id);
  }

  @GetMapping("/company/{companyId}")
  public List<PowerPlant> findByCompany(@PathVariable Long companyId) {
    return powerPlantService.findByCompany(companyId);
  }

  @GetMapping("/region/{regionId}")
  public List<PowerPlant> findByRegion(@PathVariable Long regionId) {
    return powerPlantService.findByRegion(regionId);
  }
}
