package com.energia.service;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    return countryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
  }

  public Country findById(Long id) {
    return countryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("País no encontrado"));
  }

  public Country update(Long id, Country countryDetail) {
    Country country = countryRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country no encontrada"));

    if (countryDetail.getName() != null && !countryDetail.getName().isEmpty()) {
      country.setName(countryDetail.getName());
    }
    return countryRepository.save(country);
  }
}