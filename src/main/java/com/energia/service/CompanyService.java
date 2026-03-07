package com.energia.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.energia.exception.ResourceNotFoundException;
import com.energia.model.Company;
import com.energia.model.Country;
import com.energia.repository.CompanyRepository;
import com.energia.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
  private final CompanyRepository companyRepository;
  private final CountryRepository countryRepository;

  public Company save(Company company) {

    Long countryId = company.getCountry().getId();

    Country country = countryRepository.findById(countryId)
        .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

    if (companyRepository.existsByNameAndCountryId(company.getName(), countryId)) {
      throw new ResourceNotFoundException("Company already exists in this country");
    }

    company.setCountry(country);

    return companyRepository.save(company);
  }

  public List<Company> findAll() {
    return companyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  public Company findById(Long id) {
    return companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
  }

  public List<Company> findByCountry(Long countryId) {
    return companyRepository.findByCountryId(countryId);
  }

}
