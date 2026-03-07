package com.energia.projection;

import java.math.BigDecimal;

public record KpiProjection(
    BigDecimal produccionTotal,
    Double porcentajeRenovable,
    BigDecimal capacidadSolar,
    String topPaisEolico) {
}