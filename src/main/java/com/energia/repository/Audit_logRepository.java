package com.energia.repository;

import java.util.List;

// Al utilizar esta importación se "trae" CRUD para cualquier modelo en este caso "Audit_log"
import org.springframework.data.jpa.repository.JpaRepository;
import com.energia.model.Audit_log;

public interface Audit_logRepository extends JpaRepository<Audit_log, Long> {
  List<Audit_log> findByUserId(Long userId);
}
