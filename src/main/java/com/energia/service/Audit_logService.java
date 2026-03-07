package com.energia.service;

import com.energia.model.Audit_log;
import com.energia.model.User;
import com.energia.repository.Audit_logRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Audit_logService {

  private final Audit_logRepository audit_logRepository;

  public Audit_logService(Audit_logRepository audit_logRepository) {
    this.audit_logRepository = audit_logRepository;
  }

  public Audit_log crearLog(Audit_log audit_log) {
    return audit_logRepository.save(audit_log);
  }

  public List<Audit_log> findAll() {
    return audit_logRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
  }

  public void registrarAccion(User user, String accion) {
    Audit_log log = new Audit_log();
    log.setUser(user); // JPA se encarga de extraer el ID y guardarlo en la FK
    log.setAction(accion);
    audit_logRepository.save(log);
  }

  public List<Audit_log> obtenerLogsPorUsuario(Long userId) {
    return audit_logRepository.findByUserId(userId);
  }
}
