package com.energia.service;

import com.energia.model.Country;
import com.energia.model.Region;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.energia.exception.ResourceNotFoundException;
import com.energia.repository.CountryRepository;
import com.energia.repository.RegionRepository;

import com.energia.model.User; // Asegúrate de importar tu modelo User
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {
  private final RegionRepository regionRepository;
  private final CountryRepository countryRepository;
  private final Audit_logService audit_LogService; // Inyectamos la auditoría

  public Region save(Region region) {

    Long countryId = region.getCountry().getId();

    Country country = countryRepository.findById(countryId)
        .orElseThrow(() -> new ResourceNotFoundException("Country not found"));

    if (regionRepository.existsByNameAndCountryId(region.getName(), countryId)) {
      throw new ResourceNotFoundException("Region already exists in this country");
    }

    region.setCountry(country);

    return regionRepository.save(region);
  }

  public Region update(Long id, Region regionDetail) {
    Region region = regionRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region no encontrada"));

    if (regionDetail.getName() != null && !regionDetail.getName().isEmpty()) {
      region.setName(regionDetail.getName());
    }
    return regionRepository.save(region);
  }

  @Transactional
  public void deleteById(Long id, User user) {
    // 2. Buscamos la región completa para poder ver sus hijos
    Region region = regionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Región no encontrada"));

    // 3. VALIDACIÓN CLAVE: Verificamos si tiene plantas asociadas
    if (region.getPowerPlants() != null && !region.getPowerPlants().isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "No se puede eliminar la región porque tiene plantas de energía asociadas.");
    }

    // 4. Si está limpia, borramos
    regionRepository.delete(region);

    // 5. Registramos en el log
    audit_LogService.registrarAccion(user, "ELIMINÓ REGIÓN: " + region.getName());
  }

  public List<Region> findAll() {
    return regionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  public Region findById(Long id) {
    return regionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Region not found"));
  }

  public List<Region> findByCountry(Long countryId) {
    return regionRepository.findByCountryId(countryId);
  }

}
