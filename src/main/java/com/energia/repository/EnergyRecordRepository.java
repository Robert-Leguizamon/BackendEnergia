package com.energia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable; // IMPORTANTE: Importar de org.springframework.data.domain

import com.energia.model.EnergyRecord;
import com.energia.projection.ParticipacionGlobalProjection;
import com.energia.projection.PorcentajeRenovableProjection;
import com.energia.projection.ProduccionRegionProjection;
import com.energia.projection.TendenciaSolarProjection;
import com.energia.projection.TopPaisEolicoProjection;

import org.springframework.stereotype.Repository;

@Repository
public interface EnergyRecordRepository extends JpaRepository<EnergyRecord, Long> {

  @Query("SELECT new com.energia.projection.ProduccionRegionProjection(" +
      "       r.name, et.name, SUM(er.value), mt.unit) " +
      "FROM EnergyRecord er " +
      "JOIN er.powerPlant pp " +
      "JOIN pp.region r " +
      "JOIN pp.energyType et " +
      "JOIN er.measurementType mt " +
      "WHERE er.year = :year " +
      "AND mt.name = 'Producción' " +
      "AND et.renewable = true " +
      "GROUP BY r.name, et.name, mt.unit " +
      "ORDER BY r.name ASC, SUM(er.value) DESC")
  List<ProduccionRegionProjection> findRenewableProductionByYear(@Param("year") Long year);

  @Query("SELECT new com.energia.projection.PorcentajeRenovableProjection(" +
      "r.name, " +
      "SUM(CASE WHEN mt.name = 'Producción' AND et.renewable = true THEN er.value ELSE 0 END), " +
      "SUM(CASE WHEN mt.name = 'Consumo' THEN er.value ELSE 0 END), " +
      "CAST(SUM(CASE WHEN mt.name = 'Producción' AND et.renewable = true THEN er.value ELSE 0 END) * 100.0 / " +
      "NULLIF(SUM(CASE WHEN mt.name = 'Consumo' THEN er.value ELSE 0 END), 0) AS double)) " +
      "FROM EnergyRecord er " +
      "JOIN er.powerPlant pp " +
      "JOIN pp.region r " +
      "JOIN pp.energyType et " +
      "JOIN er.measurementType mt " +
      "WHERE er.year = :year " +
      "GROUP BY r.name")
  List<PorcentajeRenovableProjection> findRenewablePercentageByRegion(@Param("year") Long year);

  @Query("SELECT new com.energia.projection.TendenciaSolarProjection(" +
      "er.year, SUM(er.value), mt.unit) " +
      "FROM EnergyRecord er " +
      "JOIN er.powerPlant pp " +
      "JOIN pp.energyType et " +
      "JOIN er.measurementType mt " +
      "WHERE et.name like '%Solar%' " +
      "AND mt.name = 'Capacidad instalada' " +
      "GROUP BY er.year, mt.unit " +
      "ORDER BY er.year ASC")
  List<TendenciaSolarProjection> findSolarCapacityTrend();

  @Query("SELECT new com.energia.projection.TopPaisEolicoProjection(" +
      "c.name, SUM(er.value), mt.unit) " +
      "FROM EnergyRecord er " +
      "JOIN er.powerPlant pp " +
      "JOIN pp.company co " + // Navegamos a través de la empresa
      "JOIN co.country c " + // Llegamos al país
      "JOIN pp.energyType et " +
      "JOIN er.measurementType mt " +
      "WHERE er.year = :year " +
      "AND mt.name = 'Producción' " +
      "AND et.name like '%Wind%'  " +
      "GROUP BY c.name, mt.unit " +
      "ORDER BY SUM(er.value) DESC")
  // Con Pageable, podrías pedir los 10 mejores, los 5 mejores o los 20 mejores
  // desde el Servicio sin cambiar la consulta.
  List<TopPaisEolicoProjection> findTopCountriesWindProduction(@Param("year") Long year, Pageable pageable);

  // @Query(value = "SELECT "+
  // "et.name AS fuente, " +
  // "SUM(er.value) AS consumoFuente, " +
  // "ROUND((SUM(er.value) * 100.0) / (SELECT SUM(er2.value) FROM energy_record
  // er2 JOIN measurement_type mt2 ON er2.measurement_type_id = mt2.id " +
  // "WHERE mt2.name = 'Consumo' AND er2.year = :year), 2) AS
  // porcentajeParticipacion " +
  // "FROM energy_record er " +
  // "JOIN measurement_type mt ON er.measurement_type_id = mt.id " +
  // "JOIN power_plant pp ON er.power_plant_id = pp.id " +
  // "JOIN energy_type et ON pp.energy_type_id = et.id " +
  // "WHERE mt.name = 'Consumo' AND er.year = :year " +
  // "GROUP BY et.name " +
  // "ORDER BY porcentajeParticipacion DESC", nativeQuery = true)
  // List<ParticipacionGlobalProjection>
  // findGlobalConsumptionShare1(@Param("year") Long year);

  @Query("SELECT new com.energia.projection.ParticipacionGlobalProjection(" +
      "et.name, " +
      "SUM(er.value), " +
      "CAST(SUM(er.value) * 100.0 / (" +
      "  SELECT SUM(er2.value) FROM EnergyRecord er2 " +
      "  JOIN er2.measurementType mt2 " +
      "  WHERE mt2.name = 'Consumo' AND er2.year = :year" +
      ") AS double)) " +
      "FROM EnergyRecord er " +
      "JOIN er.powerPlant pp " +
      "JOIN pp.energyType et " +
      "JOIN er.measurementType mt " +
      "WHERE mt.name = 'Consumo' AND er.year = :year " +
      "GROUP BY et.name " +
      "ORDER BY SUM(er.value) DESC")
  List<ParticipacionGlobalProjection> findGlobalConsumptionShare(@Param("year") Long year);

}
