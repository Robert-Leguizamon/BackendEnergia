package com.energia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.energia.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
  // Buscar por nombre
  Optional<Country> findByName(String name);

  // Validar si existe por nombre
  boolean existsByName(String name);
}
