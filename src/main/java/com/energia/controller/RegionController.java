package com.energia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energia.model.Region;
import com.energia.service.RegionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;

  @PostMapping
  public Region create(@RequestBody Region region) {
    return regionService.save(region);
  }

  @GetMapping
  public List<Region> findAll() {
    return regionService.findAll();
  }

  @GetMapping("/{id}")
  public Region findById(@PathVariable Long id) {
    return regionService.findById(id);
  }

  @GetMapping("/country/{countryId}")
  public List<Region> findByCountry(@PathVariable Long countryId) {
    return regionService.findByCountry(countryId);
  }

  @PutMapping("/{id}")
  public Region update(@PathVariable Long id, @RequestBody Region regionDetails) {
    return regionService.update(id, regionDetails);
  }
}
