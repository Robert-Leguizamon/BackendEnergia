package com.energia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.energia.model.Region;
import com.energia.service.RegionService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.energia.repository.UserRepository;
import com.energia.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;
  private final UserRepository userRepository; // Inyectamos el repositorio de usuarios

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

  @DeleteMapping("/{id}")
  // @PreAuthorize("hasRole('ADMIN')") // Opcional: Solo administradores pueden
  // borrar
  public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

    // 2. Buscamos al usuario que está operando (identificado por el JWT)
    User usuarioActual = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario autenticado no encontrado"));

    // 3. Llamamos al nuevo método del servicio pasando el ID y el Usuario
    regionService.deleteById(id, usuarioActual);

    // 4. Retornamos 204 No Content
    return ResponseEntity.noContent().build();
  }
}
