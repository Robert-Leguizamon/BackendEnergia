package com.energia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.EnergyRecord;
import com.energia.model.MeasurementType;
import com.energia.model.PowerPlant;
import com.energia.projection.PorcentajeRenovableProjection;
import com.energia.projection.ProduccionRegionProjection;
import com.energia.projection.TendenciaSolarProjection;
import com.energia.repository.EnergyRecordRepository;
import com.energia.repository.MeasurementTypeRepository;
import com.energia.repository.PowerPlantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnergyRecordService {
  private final EnergyRecordRepository energyRecordRepository;
  private final PowerPlantRepository powerPlantRepository;
  private final MeasurementTypeRepository measurementTypeRepository;

  public EnergyRecord save(EnergyRecord energyRecord) {

    Long powerPlantId = energyRecord.getPowerPlant().getId();
    Long measurementTypeId = energyRecord.getMeasurementType().getId();

    MeasurementType measurementType = measurementTypeRepository.findById(measurementTypeId)
        .orElseThrow(() -> new ResourceNotFoundException("Measurement Type not noud"));

    PowerPlant powerPlant = powerPlantRepository.findById(powerPlantId)
        .orElseThrow(() -> new ResourceNotFoundException("Power plant not found"));

    energyRecord.setPowerPlant(powerPlant);
    energyRecord.setMeasurementType(measurementType);
    return energyRecordRepository.save(energyRecord);
  }

  public List<EnergyRecord> findAll() {
    return energyRecordRepository.findAll();
  }

  /**
   * Obtiene el resumen de producción de energía renovable por región para un año
   * dado.
   * 
   * @param year Año de consulta
   * @return Lista de proyecciones con los datos agregados
   */
  public List<ProduccionRegionProjection> getRenewableProductionByYear(Long year) {
    List<ProduccionRegionProjection> results = energyRecordRepository.findRenewableProductionByYear(year);
    if (results.isEmpty()) {
      throw new ResourceNotFoundException("No se encontraron registros de producción renovable para el año " + year);
    }
    return results;
    // .get(0); // Retorna el primer resultado, aunque podría retornar la lista
    // completa si se desea
  }

  /**
   * Obtiene el balance de energía por región: producción renovable vs consumo
   * total, calculando el porcentaje de participación renovable.
   * 
   * @param year Año de consulta
   * @return Lista de proyecciones con los cálculos realizados
   */
  // @Transactional(readOnly = true) para que Spring optimice la conexión a la
  // base de datos, ya que es una operación de solo lectura.
  public List<PorcentajeRenovableProjection> getRenewablePercentageByRegion(Long year) {
    List<PorcentajeRenovableProjection> results = energyRecordRepository.findRenewablePercentageByRegion(year);

    if (results.isEmpty()) {
      throw new ResourceNotFoundException("No se encontraron registros para calcular el porcentaje en el año " + year);
    }

    return results;
  }

  /**
   * Obtiene la evolución histórica de la capacidad solar instalada.
   * 
   * @return Lista de proyecciones ordenadas por año
   */
  // @Transactional(readOnly = true) para que Spring optimice la conexión a la
  // base de datos, ya que es una operación de solo lectura.
  public List<TendenciaSolarProjection> getSolarCapacityTrend() {
    List<TendenciaSolarProjection> results = energyRecordRepository.findSolarCapacityTrend();

    if (results.isEmpty()) {
      throw new ResourceNotFoundException("No se encontraron registros de capacidad instalada para energía solar.");
    }

    return results;
  }
}
