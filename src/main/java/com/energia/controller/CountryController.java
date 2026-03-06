package com.energia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.Country;
import com.energia.service.CountryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/countrys")
@RequiredArgsConstructor
public class CountryController {
  private final CountryService countryService;

  @PostMapping
  public ResponseEntity<Country> create(@RequestBody Country country) {
    return ResponseEntity.ok(countryService.save(country));
  }

  @GetMapping
  public ResponseEntity<List<Country>> findAll() {
    return ResponseEntity.ok(countryService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Country> findById(@PathVariable Long id) {
    return ResponseEntity.ok(countryService.findById(id));
  }

  
  @PutMapping("/{id}")
  public Country update(@PathVariable Long id, @RequestBody Country countryDetails) {
    return countryService.update(id, countryDetails);
  }
}
