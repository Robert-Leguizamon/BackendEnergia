package com.energia.service;

import org.springframework.stereotype.Service;

import com.energia.repository.MeasurementTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeasurementTypeService {
  public final MeasurementTypeRepository measurementTypeRepository;

}
