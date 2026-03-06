package com.energia.projection;

import java.math.BigDecimal;

public record PorcentajeRenovableProjection(
    String region,
    BigDecimal produccionRenovable,
    BigDecimal consumoTotal,
    Double porcentaje // El resultado del cálculo
) {
}