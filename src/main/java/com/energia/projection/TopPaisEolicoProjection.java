package com.energia.projection;

import java.math.BigDecimal;

public record TopPaisEolicoProjection(
    String pais,
    BigDecimal produccionTotal,
    String unidad) {
}