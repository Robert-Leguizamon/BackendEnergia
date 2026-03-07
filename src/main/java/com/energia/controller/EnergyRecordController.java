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
import com.energia.projection.ParticipacionGlobalProjection;
import com.energia.projection.PorcentajeRenovableProjection;
import com.energia.projection.ProduccionRegionProjection;
import com.energia.projection.TendenciaSolarProjection;
import com.energia.projection.TopPaisEolicoProjection;
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
  public ResponseEntity<List<PorcentajeRenovableProjection>> getRenewablePercentageByRegion(
      @PathVariable Integer year) {
    List<PorcentajeRenovableProjection> reporte = energyRecordService.getRenewablePercentageByRegion(year);
    return ResponseEntity.ok(reporte);
  }

  /**
   * Endpoint para obtener la tendencia histórica de la capacidad solar instalada.
   * URL: GET /api/energyrecords/tendencia-solar
   */
  @GetMapping("/tendencia-solar")
  public ResponseEntity<List<TendenciaSolarProjection>> getSolarTrend() {
    List<TendenciaSolarProjection> reporte = energyRecordService.getSolarCapacityTrend();
    return ResponseEntity.ok(reporte);
  }

  /**
   * Endpoint para obtener el ranking de los 10 países con mayor producción
   * eólica.
   * URL Ejemplo: GET /api/energyrecords/top-10-paises-eolico/2021
   */
  @GetMapping("/top-10-paises-eolico/{year}")
  public ResponseEntity<List<TopPaisEolicoProjection>> getTop10WindProduction(@PathVariable Long year) {
    List<TopPaisEolicoProjection> ranking = energyRecordService.getTop10WindProductionByYear(year);
    return ResponseEntity.ok(ranking);
  }

  /**
   * Endpoint para obtener el share (participación) de cada fuente en el consumo
   * global.
   * URL: GET /api/energyrecords/participacion-consumo/{year}
   */
  @GetMapping("/participacion-consumo/{year}")
  public ResponseEntity<List<ParticipacionGlobalProjection>> getGlobalShare(@PathVariable Long year) {
    List<ParticipacionGlobalProjection> reporte = energyRecordService.getGlobalConsumptionShare(year);
    return ResponseEntity.ok(reporte);
  }
}
