package com.energia.model;

// para manejo de Timestamp
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// esto sirve para poder utilizar esta clase con cualquier motor de base de datos.
import jakarta.persistence.*;

// @ esto se llama un decorador
// @Entity indica que es un valor persistente.
@Entity
@Table(name = "users")
public class User {
  // Definición de las columnas
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // se recibe en request, pero no se envía en response
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  // no necesita ni get ni set por que en base de datos se realiza automáticamente
  // este es para cuando se crea un registro
  // Se usa createdAt (camelCase) porque es la convención de nombres en Java.
  // Es el nombre que usarás en tu código para hacer cosas como
  // objeto.getCreatedAt().
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createAt;
  // este es para cuando se actualiza el registro
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false, updatable = false)
  private LocalDateTime updateAt;

  public User() {
  };

  // Métodos
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // SI NO SE QUIERE QUE LA API DEVUELVA UN CAMPO NO SE GENERA SU GET.
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  // --- CAMBIO OPCIONAL (Para bidireccionalidad) ---
  // mappedBy dice que la relación ya está definida en el campo "user" de la clase
  // Audit_log
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore // Esto por que aparece muchos log en un bucle...
  private List<Audit_log> auditLogs;

  // Getter y Setter para la lista
  public List<Audit_log> getAuditLogs() {
    return auditLogs;
  }

  public void setAuditLogs(List<Audit_log> auditLogs) {
    this.auditLogs = auditLogs;
  }

}
