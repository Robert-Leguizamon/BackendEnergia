package com.energia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.EnergyType;
import com.energia.repository.EnergyTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnergyTypeService {
  public final EnergyTypeRepository energyTypeRepository;

  public EnergyType save(EnergyType energyType) {
    if (energyTypeRepository.existsByName(energyType.getName())) {
      throw new ResourceNotFoundException("el tipo de energía ya existe");
    }
    return energyTypeRepository.save(energyType);
  }

  public List<EnergyType> findAll() {
    return energyTypeRepository.findAll();
  }

  public EnergyType findById(Long id) {
    return energyTypeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("EnergyType not found"));
  }

  public List<EnergyType> findByRenewable(Boolean renewable) {
    return energyTypeRepository.findByRenewable(renewable);
  }

}
