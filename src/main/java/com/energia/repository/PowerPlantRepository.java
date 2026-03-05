package com.energia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.PowerPlant;

public interface PowerPlantRepository extends JpaRepository<PowerPlant, Long> {

  // Validar si existe por nombre
  boolean existsByName(String name);

  // Buscar plantas por empresa
  List<PowerPlant> findByCompanyId(Long companyId);

  // Buscar plantas por región
  List<PowerPlant> findByRegionId(Long regionId);

  // Validar planta única por empresa
  Optional<PowerPlant> findByNameAndCompanyId(String name, Long companyId);

  boolean existsByNameAndCompanyId(String name, Long companyId);
}
