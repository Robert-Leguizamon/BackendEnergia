package com.energia.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energia.projection.ChartDataProjection;
import com.energia.projection.DashboardProjection;
import com.energia.projection.KpiProjection;
import com.energia.repository.EnergyRecordRepository;
import com.energia.projection.PorcentajeRenovableProjection;;

@Service
public class DashboardService {
  @Autowired
  private EnergyRecordRepository repository;

  public DashboardProjection getDashboardData(Integer year, String country, String energyType) {
    // Si el filtro es "Global" o "Todas", lo pasamos como null a la DB
    String countryFilter = (country == null || country.equalsIgnoreCase("Global")) ? null : country;
    String typeFilter = (energyType == null || energyType.equalsIgnoreCase("Todas")) ? null : energyType;

    // 1. Obtener KPIs (Puedes llamar a varios métodos del repo)
    BigDecimal prodTotal = repository.getGlobalProduction(year, countryFilter);
    // ... (otros cálculos para % Renovable y Capacidad Solar)
    KpiProjection kpis = new KpiProjection(prodTotal, 62.0, new BigDecimal(890), "Alemania DE");

    // 2. Obtener Series para el Gráfico (Siempre traemos el historial para la
    // línea)
    List<ChartDataProjection> series = repository.findHistoricalTrend(countryFilter, typeFilter);

    // 3. Obtener Datos para la Tabla Inferior
    List<PorcentajeRenovableProjection> tabla = repository.findRenewablePercentageByRegion(year);

    return new DashboardProjection(kpis, series, tabla);
  }
}
