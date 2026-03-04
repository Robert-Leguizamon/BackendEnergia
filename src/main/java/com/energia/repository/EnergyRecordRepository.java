package com.energia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.energia.model.EnergyRecord;
import com.energia.projection.ProduccionRegionProjection;

public interface EnergyRecordRepository extends JpaRepository<EnergyRecord, Long> {

  @Query(value = "SELECT " +
      "        r.name AS region,  " +
      "        et.name AS fuenteEnergia,  " +
      "        SUM(er.value) AS produccionTotal,  " +
      "        mt.unit AS unidad " +
      "    FROM energy_record er " +
      "    JOIN measurement_type mt ON er.measurement_type_id = mt.id " +
      "    JOIN power_plant pp ON er.power_plant_id = pp.id " +
      "    JOIN region r ON pp.region_id = r.id " +
      "    JOIN energy_type et ON pp.energy_type_id = et.id " +
      "    WHERE er.year = :year  " +
      "      AND mt.name = 'Producción'  " +
      "      AND et.renewable = true " +
      "    GROUP BY r.name, et.name, mt.unit " +
      "    ORDER BY r.name ASC, produccionTotal DESC ", nativeQuery = true)
  List<ProduccionRegionProjection> findRenewableProductionByYear(@Param("year") Long year);

}
