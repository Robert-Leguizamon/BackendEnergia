package com.energia.controller;

import com.energia.model.Audit_log;
import com.energia.service.Audit_logService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit_log")

public class Audit_logController {
  private final Audit_logService audit_logService;

  public Audit_logController(Audit_logService audit_logService) {
    this.audit_logService = audit_logService;
  }

  @GetMapping()
  public List<Audit_log> findAll() {
    return audit_logService.findAll();
  }

  // Ejemplo: GET /api/audit_log/user/5
  @GetMapping("/{userId}")
  public List<Audit_log> findByUserId(@PathVariable Long userId) {
    return audit_logService.obtenerLogsPorUsuario(userId);
  }
}
