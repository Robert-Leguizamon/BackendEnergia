package com.energia.projection;

import java.math.BigDecimal;

/**
 * Record que actúa como Proyección para la consulta de energía.
 * Java mapeará automáticamente las columnas del SQL 'AS nombre'
 * a estos campos.
 */
public record ProduccionRegionProjection(
    String region,
    String fuenteEnergia,
    BigDecimal produccionTotal,
    String unidad) {
}