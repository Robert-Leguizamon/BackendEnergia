// package com.energia.config;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// @Configuration // Este decorardor hace que se cargue automáticamente al iniciar la app
// @EnableMethodSecurity // Activa la seguridad por anotaciones en métodos
// public class SecurityConfig {
//   @Bean
//   public PasswordEncoder passwordEncoder() { // Crea un obejeto global para encriptar contraseñas
//     return new BCryptPasswordEncoder();
//   }

//   @Bean
//   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//     http
//         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//         .csrf(csrf -> csrf.disable()) // si no se desactiva falla POST/PUT/DELETE
//         .authorizeHttpRequests(auth -> auth
//             // .requestMatchers("/api/users/**").permitAll() // los ** funcionan pero por
//             // seguridad es mejor utilizar cada ruta por searado
//             .requestMatchers("/api/audit_log/{userid}").permitAll()
//             .requestMatchers("/api/audit_log").permitAll()
//             .requestMatchers("/api/users").permitAll()
//             .requestMatchers("/api/users/login").permitAll()
//             .requestMatchers("/api/users/{id}").permitAll()
//             .requestMatchers("/api/countrys/**").permitAll()
//             .requestMatchers("/api/regions/**").permitAll()
//             .requestMatchers("/api/companys/**").permitAll()
//             .requestMatchers("/api/powerplants/**").permitAll()
//             .requestMatchers("/api/energytypes/**").permitAll()
//             .requestMatchers("/api/measurementypes/**").permitAll()
//             .requestMatchers("/api/energyrecords/**").permitAll()
//             .requestMatchers("/api/porcentaje-renovable/**").permitAll()
//             .requestMatchers("/api/top-10-paises-eolico/**").permitAll()
//             .requestMatchers("/api/participacion-consumo/**").permitAll()
//             .requestMatchers("/api/dashboard/**").permitAll()
//             // .requestMatchers("/api/produccion-renovable/**").permitAll()
//             .anyRequest().authenticated())
//         .formLogin(form -> form.disable())
//         .httpBasic(basic -> basic.disable());
//     return http.build();
//   }

//   @Bean
//   public CorsConfigurationSource corsConfigurationSource() {
//     CorsConfiguration configuration = new CorsConfiguration();
//     configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
//     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//     configuration.setAllowedHeaders(List.of("*"));
//     configuration.setAllowCredentials(true);

//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", configuration);
//     return source;
//   }
// }
package com.energia.config;

import java.util.List;

import com.energia.repository.UserRepository; // Asegúrate de que el paquete sea correcto
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  // 1. Inyectamos el filtro de JWT y el repositorio de usuarios
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final UserRepository userRepository;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserRepository userRepository) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.userRepository = userRepository;
  }

  // 2. Definimos cómo Spring buscará a los usuarios para validar el Token
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .map(user -> org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 3. Configuramos el proveedor de autenticación
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())

        // 4. IMPORTANTE: Cambiamos a STATELESS (sin sesiones en servidor)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth
            // Rutas PÚBLICAS (puedes añadir más si quieres que sean libres)
            .requestMatchers("/api/users/login").permitAll()
            .requestMatchers("/api/users").permitAll() // Para permitir registro

            // 5. ¡PROTEGEMOS TODO LO DEMÁS!
            // Ahora todas las rutas de Dashboard, Audit, etc., requerirán Token
            .anyRequest().authenticated())

        .authenticationProvider(authenticationProvider())
        // 6. Ponemos nuestro filtro JWT antes del filtro de usuario/password
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    // Añadimos "Authorization" explícitamente a los headers permitidos para que el
    // frontend pueda enviar el token
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}