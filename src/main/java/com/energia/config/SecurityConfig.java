package com.energia.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration // Este decorardor hace que se cargue automáticamente al iniciar la app
@EnableMethodSecurity // Activa la seguridad por anotaciones en métodos
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() { // Crea un obejeto global para encriptar contraseñas
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors-> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable()) // si no se desactiva falla POST/PUT/DELETE
        .authorizeHttpRequests(auth -> auth
            // .requestMatchers("/api/users/**").permitAll() // los ** funcionan pero por
            // seguridad es mejor utilizar cada ruta por searado
            .requestMatchers("/api/audit_log/{userid}").permitAll()
            .requestMatchers("/api/audit_log").permitAll()
            .requestMatchers("/api/users").permitAll()
            .requestMatchers("/api/users/login").permitAll()
            .requestMatchers("/api/users/{id}").permitAll()
            .requestMatchers("/api/countrys/**").permitAll()
            .requestMatchers("/api/regions/**").permitAll()
            .requestMatchers("/api/companys/**").permitAll()
            .requestMatchers("/api/powerplants/**").permitAll()
            .requestMatchers("/api/energytypes/**").permitAll()
            .requestMatchers("/api/measurementypes/**").permitAll()
            .requestMatchers("/api/energyrecords/**").permitAll()
            // .requestMatchers("/api/produccion-renovable/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());
    return http.build();
  }

  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
