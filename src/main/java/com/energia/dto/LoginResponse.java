package com.energia.dto;

import com.energia.model.Role;

public class LoginResponse {
  private String token;
  private String username;
  private Role role;

  // Puedes agregar más campos como el rol
  public LoginResponse(String token, String username, Role role) {
    this.token = token;
    this.username = username;
    this.role = role;
  }

  // Getters y Setters
  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }

  public Role getRole() {
    return role;
  }
}
