package com.energia.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

  // Asegúrate de que esta llave sea larga (mínimo 32 caracteres)
  private static final String SECRET_KEY = "tu_llave_secreta_super_segura_que_debe_ser_muy_larga_para_evitar_errores";

  // GENERAR EL TOKEN
  public String generateToken(String username) {
    // Map<String, Object> extraClaims = new HashMap<>();
    // .claims(extraClaims) // cambio: antes era setClaims

    return Jwts.builder()
        .subject(username) // cambio: antes era setSubject
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(getSignInKey()) // cambio: ya no necesita SignatureAlgorithm
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(String token, String username) {
    final String usernameFromToken = extractUsername(token);
    return (usernameFromToken.equals(username)) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    // cambio: nueva sintaxis 0.12.x para leer el token
    return Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}