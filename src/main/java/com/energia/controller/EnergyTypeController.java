package com.energia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.EnergyType;
import com.energia.service.EnergyTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/energytypes")
@RequiredArgsConstructor
public class EnergyTypeController {
  private final EnergyTypeService energyTypeService;

  @PostMapping
  public ResponseEntity<EnergyType> create(@RequestBody EnergyType energyType) {
    return ResponseEntity.ok(energyTypeService.save(energyType));
  }

  @GetMapping
  public ResponseEntity<List<EnergyType>> findAll() {
    return ResponseEntity.ok(energyTypeService.findAll());
  }

  @GetMapping("/{id}")
  public EnergyType findById(@PathVariable Long id) {
    return energyTypeService.findById(id);
  }

  @GetMapping("/renewable/{renewable}")
  public List<EnergyType> findByRenewable(@PathVariable Boolean renewable) {
    return energyTypeService.findByRenewable(renewable);
  }
}
