package com.energia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.MeasurementType;

public interface MeasurementTypeRepository extends JpaRepository<MeasurementType, Long> {
  // Validar si existe por nombre
  boolean existsByName(String name);

  Optional<MeasurementType> findByName(String name);
}
