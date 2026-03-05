package com.energia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.EnergyType;

public interface EnergyTypeRepository extends JpaRepository<EnergyType, Long> {

  Optional<EnergyType> findByName(String name);

  // Validar si existe por nombre
  boolean existsByName(String name);

  List<EnergyType> findByRenewable(Boolean renewable);

}
