package com.energia.service;

import java.util.List;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.PowerPlant;

import org.springframework.stereotype.Service;
import com.energia.repository.PowerPlantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PowerPlantService {

  private final PowerPlantRepository powerPlantRepository;

  public PowerPlant save(PowerPlant powerPlant) {
    if (powerPlantRepository.existsByName(powerPlant.getName())) {
      throw new ResourceNotFoundException("la planta ya existe");
    }
    return powerPlantRepository.save(powerPlant);
  }

  public List<PowerPlant> findAll() {
    return powerPlantRepository.findAll();
  }

}
