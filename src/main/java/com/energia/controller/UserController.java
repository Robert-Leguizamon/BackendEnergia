package com.energia.controller;

import com.energia.dto.LoginRequest;
import com.energia.dto.LoginResponse;
import com.energia.model.User;
import com.energia.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

// estas para manejar los errores
import org.springframework.web.server.ResponseStatusException;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // <--- ESTA ES LA LÍNEA CLAVE
// @CrossOrigin(origins = "http://localhost:4200") // <-- Esto para que se pueda
// conectar con el frontend de angular

public class UserController {
  // private final UserRepository userRepository;
  // Se invoca el servicio UserService por que se implementó un cambio en la
  // ejecución "nomal" de springboot
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.crearUsuario(user));
  }

  @GetMapping
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public User findByID(@PathVariable Long id) {
    // return userService.findById(id).orElseThrow(() -> new
    // RuntimeException("Usuario no encontrado."));
    return userService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));
  }

  @PutMapping("/{id}")
  public User update(@PathVariable Long id, @RequestBody User userDetails) {
    return userService.update(id, userDetails);
  }

  // el ? se utiliza desde la url
  // @PostMapping("/login")
  // public ResponseEntity<?> login(@RequestBody LoginRequest request) {
  // String response = userService.login(request);
  // return ResponseEntity.ok(response);

  // }
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    // LoginResponse es una clase DTO que contiene el token JWT y posiblemente otros
    // datos relacionados con el usuario
    LoginResponse response = userService.login(request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    // 1. Verificar si existe (o usar findById directamente)
    User user = userService.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

    // 2. Borrar de verdad
    userService.delete(user);

    // 3. Retornar 204 No Content (el estándar profesional)
    return ResponseEntity.noContent().build();
  }

  /*
   * @RestController
   * 
   * @RequestMapping("/api/users")
   * public class UserController {
   * 
   * private final UserService userService;
   * 
   * // Acepta tanto PUT como PATCH
   * 
   * @RequestMapping(value = "/{id}", method = {RequestMethod.PUT,
   * RequestMethod.PATCH})
   * public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User
   * userDetails) {
   * return userService.findById(id)
   * .map(existingUser -> {
   * // Lógica de actualización parcial: solo cambia si el dato no es nulo
   * if (userDetails.getUsername() != null) {
   * existingUser.setUsername(userDetails.getUsername());
   * }
   * if (userDetails.getEmail() != null) {
   * existingUser.setEmail(userDetails.getEmail());
   * }
   * 
   * User updatedUser = userService.save(existingUser);
   * return ResponseEntity.ok(updatedUser);
   * })
   * .orElseGet(() -> ResponseEntity.notFound().build());
   * }
   * }
   */

}
