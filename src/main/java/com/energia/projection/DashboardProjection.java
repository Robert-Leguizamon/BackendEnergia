package com.energia.projection;

import java.util.List;

public record DashboardProjection(
    KpiProjection kpis,
    List<ChartDataProjection> seriesHistorica,
    List<PorcentajeRenovableProjection> tablaResumen) {
}
