package com.energia.service;

import com.energia.model.Country;
import com.energia.model.Region;

import java.util.List;

import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.repository.CountryRepository;
import com.energia.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {
  private final RegionRepository regionRepository;
  private final CountryRepository countryRepository;

  public Region save(Region region) {

    Long countryId = region.getCountry().getId();

    Country country = countryRepository.findById(countryId)
        .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

    if (regionRepository.existsByNameAndCountryId(region.getName(), countryId)) {
      throw new ResourceNotFoundException("Region already exists in this country");
    }

    region.setCountry(country);

    return regionRepository.save(region);
  }

  public List<Region> findAll() {
    return regionRepository.findAll();
  }

  public Region findById(Long id) {
    return regionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Region not found"));
  }

  public List<Region> findByCountry(Long countryId) {
    return regionRepository.findByCountryId(countryId);
  }

}
