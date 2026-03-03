package com.energia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.EnergyRecord;
import com.energia.model.MeasurementType;
import com.energia.model.PowerPlant;
import com.energia.repository.EnergyRecordRepository;
import com.energia.repository.MeasurementTypeRepository;
import com.energia.repository.PowerPlantRepository;

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
}
