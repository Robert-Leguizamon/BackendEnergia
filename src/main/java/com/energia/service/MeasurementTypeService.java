package com.energia.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.MeasurementType;
import com.energia.repository.MeasurementTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeasurementTypeService {
  public final MeasurementTypeRepository measurementTypeRepository;

  public MeasurementType save(MeasurementType measurementType) {
    if (measurementTypeRepository.existsByName(measurementType.getName())) {
      throw new ResourceNotFoundException("el measuremente type ya existe");
    }
    return measurementTypeRepository.save(measurementType);
  }

  public List<MeasurementType> findAll() {
    return measurementTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  public MeasurementType findById(Long id) {
    return measurementTypeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("MeasurementType not found"));
  }
}
