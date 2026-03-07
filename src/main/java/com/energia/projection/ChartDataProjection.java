package com.energia.projection;

import java.math.BigDecimal;

public record ChartDataProjection(
    Integer year,
    String tipoEnergia,
    BigDecimal valor) {
}