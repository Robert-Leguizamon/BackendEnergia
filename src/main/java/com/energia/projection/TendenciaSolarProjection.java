package com.energia.projection;

import java.math.BigDecimal;

public record TendenciaSolarProjection(
    Integer anio,
    BigDecimal capacidadTotal,
    String unidad) {
}