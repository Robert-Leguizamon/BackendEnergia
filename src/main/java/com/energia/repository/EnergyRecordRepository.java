package com.energia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.energia.model.EnergyRecord;
import com.energia.projection.PorcentajeRenovableProjection;
import com.energia.projection.ProduccionRegionProjection;

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

}
