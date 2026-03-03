package com.energia.service;

import org.springframework.stereotype.Service;

import java.util.List;
import com.energia.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

import com.energia.model.Country;
import com.energia.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CountryService {

  private final CountryRepository countryRepository;

  public Country save(Country country) {

    if (countryRepository.existsByName(country.getName())) {
      throw new ResourceNotFoundException("el país ya existe");
    }

    return countryRepository.save(country);
  }

  public List<Country> findAll() {
    return countryRepository.findAll();
  }

  public Country findById(Long id) {
    return countryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("País no encontrado"));
  }
}