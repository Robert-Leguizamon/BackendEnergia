package com.energia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.energia.projection.DashboardProjection;
import com.energia.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*") // Para que el Frontend pueda conectarse
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  @GetMapping
  public ResponseEntity<DashboardProjection> getDashboard(
      @RequestParam Integer year,
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String energyType) {

    return ResponseEntity.ok(dashboardService.getDashboardData(year, country, energyType));
  }
}