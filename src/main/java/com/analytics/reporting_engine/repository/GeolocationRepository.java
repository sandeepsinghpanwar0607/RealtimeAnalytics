package com.analytics.reporting_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.analytics.reporting_engine.entity.Geolocation;

public interface GeolocationRepository extends JpaRepository<Geolocation, Long> {
}