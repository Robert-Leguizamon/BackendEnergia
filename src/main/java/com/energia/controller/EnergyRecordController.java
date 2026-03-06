package com.energia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.EnergyRecord;
import com.energia.projection.PorcentajeRenovableProjection;
import com.energia.projection.ProduccionRegionProjection;
import com.energia.service.EnergyRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/energyrecords")
@RequiredArgsConstructor
public class EnergyRecordController {
  private final EnergyRecordService energyRecordService;

  @PostMapping
  public EnergyRecord create(@RequestBody EnergyRecord energyRecord) {
    return energyRecordService.save(energyRecord);
  }

  @GetMapping
  public List<EnergyRecord> findAll() {
    return energyRecordService.findAll();

  }

  @GetMapping("/produccion-renovable/{year}")
  public List<ProduccionRegionProjection> getRenewableProductionByYear(@PathVariable Long year) {
    return energyRecordService.getRenewableProductionByYear(year);
  }

  @GetMapping("/porcentaje-renovable/{year}")
  public ResponseEntity<List<PorcentajeRenovableProjection>> getRenewablePercentageByRegion(@PathVariable Long year) {
    List<PorcentajeRenovableProjection> reporte = energyRecordService.getRenewablePercentageByRegion(year);
    return ResponseEntity.ok(reporte);
  }
}
