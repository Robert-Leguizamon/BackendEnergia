package com.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.EnergyType;

public interface EnergyTypeRepository extends JpaRepository<EnergyType, Long> {

  // Validar si existe por nombre
  boolean existsByName(String name);

}
