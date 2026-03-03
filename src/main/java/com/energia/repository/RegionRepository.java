package com.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.Region;
import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
  // Buscar todas las regiones de un país
  List<Region> findByCountryId(Long countryId);

  // Validar región única por país
  Optional<Region> findByNameAndCountryId(String name, Long countryId);

  boolean existsByNameAndCountryId(String name, Long countryId);
}
