package com.energia.model;

// para manejo de Timestamp
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// esto sirve para poder utilizar esta clase con cualquier motor de base de datos.
import jakarta.persistence.*;

// @ esto se llama un decorador
// @Entity indica que es un valor persistente.
@Entity
@Table(name = "audit_log")
public class Audit_log {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String action;

  // no necesita ni get // este es paracuando se cre a un registro
  // Se usa createdAt (camelCase) porque es la convención de nombres en Java.
  // Es el nombre que usarás en tu código para hacer cosas como
  // objeto.getCreatedAt().
  @CreationTimestamp
  @Column(name = "action_date", nullable = false, updatable = false)
  private LocalDateTime actionDate;

  public Audit_log() {
  };

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getActionDate() {
    return actionDate;
  }

  // no se utiliza @Column(nullable = false) por utilizar @ManyToOne por que se
  // utiliza @JoinColumn
  // En lugar de: private Long user_id;
  @ManyToOne(fetch = FetchType.LAZY) // LAZY carga el usuario solo cuando lo necesitas
  @JoinColumn(name = "user_id", nullable = false) // Aquí se define el nombre de la FK en la DB
  // Esta línea evita errores al convertir a JSON y evita bucles infinitos
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private User user;

}
