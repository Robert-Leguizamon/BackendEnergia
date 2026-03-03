package com.energia.repository;

import java.util.Optional;

// Al utilizar esta importación se "trae" CRUD para cualquier modelo en este caso "User"
import org.springframework.data.jpa.repository.JpaRepository;
import com.energia.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  // se crea un nuevo metodo solo para buscar por el campo que viene del json del
  // request en este caso { "username":"....", "password":"...."} que lo envió el
  // UserService cuando invoca findByUsername
  Optional<User> findByUsername(String username);

}
