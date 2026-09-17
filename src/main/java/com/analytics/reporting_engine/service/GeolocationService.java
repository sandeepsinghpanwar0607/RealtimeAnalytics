package com.analytics.reporting_engine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.reporting_engine.entity.Geolocation;
import com.analytics.reporting_engine.repository.GeolocationRepository;

@Service
public class GeolocationService {

    private final GeolocationRepository geolocationRepository;

    public GeolocationService(GeolocationRepository geolocationRepository) {
        this.geolocationRepository = geolocationRepository;
    }

    public Geolocation save(Geolocation geolocation) {
        return geolocationRepository.save(geolocation);
    }

    public List<Geolocation> findAll() {
        return geolocationRepository.findAll();
    }

    public Geolocation findById(Long id) {
        return geolocationRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        geolocationRepository.deleteById(id);
    }
}