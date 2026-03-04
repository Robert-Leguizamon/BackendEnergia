package com.energia.service;

import com.energia.dto.LoginRequest;
import com.energia.dto.LoginResponse;
// import com.energia.model.Audit_log;
import com.energia.model.User;
import com.energia.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import com.energia.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Audit_logService audit_LogService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      Audit_logService audit_LogService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.audit_LogService = audit_LogService;
  }

  public User crearUsuario(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User newUser = userRepository.save(user);
    audit_LogService.registrarAccion(newUser, "USUARIO_CREADO_SISTEMA");
    return newUser;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public User update(Long id, User userDetails) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));
    // Lógica de actualización parcial: solo cambia si el dato no es nulo
    if (userDetails.getUsername() != null && !userDetails.getUsername().trim().isEmpty()) {
      user.setUsername(userDetails.getUsername());
    }
    if (userDetails.getEmail() != null && !userDetails.getEmail().trim().isEmpty()) {
      user.setEmail(userDetails.getEmail());
    }
    if (userDetails.getPassword() != null &&
        !userDetails.getPassword().trim().isEmpty()) {
      user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    }
    if (userDetails.getRole() != null) {
      user.setRole(userDetails.getRole());
    }

    User upUser = userRepository.save(user);
    audit_LogService.registrarAccion(upUser, "PERFIL_ACTUALIZADO");

    return upUser;
  }

  // public String login(LoginRequest request) {
  public LoginResponse login(LoginRequest request) {
    Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
    if (optionalUser.isEmpty()) {
      throw new ResourceNotFoundException("Usuario no encontrado");
    }
    User user = optionalUser.get();
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResourceNotFoundException("Contraseña incorrecta"); // esto no se debe colocar por seguridad RELC
    }

    // --- AQUÍ CREAMOS EL LOG AUTOMÁTICAMENTE ---
    audit_LogService.registrarAccion(user, "INICIO_SESION_EXITOSO");

    // return "Login correcto";
    return new LoginResponse("sin_token", user.getUsername(), user.getRole());
  }

  // @PostMapping("/login")
  // public ResponseEntity<?> login(@RequestBody LoginRequest request) {
  // Optional<User> optionalUser =
  // userRepository.findByUsername(request.getUsername());

  // // 1. Validaciones (Manejo de errores con códigos HTTP)
  // if (optionalUser.isEmpty() || !passwordEncoder.matches(request.getPassword(),
  // optionalUser.get().getPassword())) {
  // // Es mejor devolver 401 Unauthorized por seguridad genérica
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales
  // inválidas");
  // }

  // User user = optionalUser.get();

  // // 2. Generar el Token (JWT es el estándar recomendado)
  // String token = jwtService.generateToken(user); // Debes tener un servicio que
  // cree el JWT

  // // 3. Registrar auditoría
  // audit_LogService.registrarAccion(user, "INICIO_SESION_EXITOSO");

  // // 4. Retornar el objeto JSON
  // return ResponseEntity.ok(new LoginResponse(token, user.getUsername()));
  // }

}
