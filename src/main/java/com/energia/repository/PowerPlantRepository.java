package com.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.PowerPlant;

public interface PowerPlantRepository extends JpaRepository<PowerPlant, Long> {

  // Validar si existe por nombre
  boolean existsByName(String name);
}
