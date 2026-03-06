package com.energia.projection;

import java.math.BigDecimal;

public record ParticipacionGlobalProjection(
    String fuente,
    BigDecimal consumoFuente,
    Double porcentajeParticipacion) {
}