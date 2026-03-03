package com.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energia.model.EnergyRecord;

public interface EnergyRecordRepository extends JpaRepository<EnergyRecord, Long> {

}
