package com.energia.service;

import java.util.List;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.Company;
import com.energia.model.EnergyType;
import com.energia.model.PowerPlant;
import com.energia.model.Region;

import org.springframework.stereotype.Service;

import com.energia.repository.CompanyRepository;
import com.energia.repository.EnergyTypeRepository;
import com.energia.repository.PowerPlantRepository;
import com.energia.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PowerPlantService {

  private final PowerPlantRepository powerPlantRepository;
  private final CompanyRepository companyRepository;
  private final EnergyTypeRepository energyTypeRepository;
  private final RegionRepository regionRepository;

  public PowerPlant save(PowerPlant powerPlant) {

    Long companyId = powerPlant.getCompany().getId();

    Company company = companyRepository.findById(companyId)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

    Long energyTypeId = powerPlant.getEnergyType().getId();

    EnergyType energyType = energyTypeRepository.findById(energyTypeId)
        .orElseThrow(() -> new ResourceNotFoundException("Energy Type not found"));

    Long regionId = powerPlant.getRegion().getId();
    Region region = regionRepository.findById(regionId)
        .orElseThrow(() -> new ResourceNotFoundException("Region not found"));

    if (powerPlantRepository.existsByName(powerPlant.getName())) {
      throw new ResourceNotFoundException("la planta ya existe");
    }

    powerPlant.setCompany(company);
    powerPlant.setEnergyType(energyType);
    powerPlant.setRegion(region);

    return powerPlantRepository.save(powerPlant);
  }

  public List<PowerPlant> findAll() {
    return powerPlantRepository.findAll();
  }

  public PowerPlant findById(Long id) {
    return powerPlantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("PowerPlant not found"));
  }

  public List<PowerPlant> findByCompany(Long companyId) {
    return powerPlantRepository.findByCompanyId(companyId);
  }

  public List<PowerPlant> findByRegion(Long regionId) {
    return powerPlantRepository.findByRegionId(regionId);
  }
}
