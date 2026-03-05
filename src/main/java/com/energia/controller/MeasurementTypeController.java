package com.energia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.MeasurementType;
import com.energia.service.MeasurementTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/measurementypes")
@RequiredArgsConstructor
public class MeasurementTypeController {
  private final MeasurementTypeService measurementTypeService;

  @PostMapping
  public ResponseEntity<MeasurementType> create(@RequestBody MeasurementType measurementType) {
    return ResponseEntity.ok(measurementTypeService.save(measurementType));
  }

  @GetMapping
  public ResponseEntity<List<MeasurementType>> findAll() {
    return ResponseEntity.ok(measurementTypeService.findAll());
  }

  @GetMapping("/{id}")
  public MeasurementType findById(@PathVariable Long id) {
    return measurementTypeService.findById(id);
  }
}
