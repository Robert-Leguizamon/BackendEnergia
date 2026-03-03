package com.energia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.Company;
import com.energia.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companys")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public Company create(@RequestBody Company company) {
    return companyService.save(company);
  }

  @GetMapping
  public List<Company> findAll() {
    return companyService.findAll();
  }

  @GetMapping("/{id}")
  public Company findById(@PathVariable Long id) {
    return companyService.findById(id);
  }

  @GetMapping("/country/{countryId}")
  public List<Company> findByCountry(@PathVariable Long countryId) {
    return companyService.findByCountry(countryId);
  }

}
