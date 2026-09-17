package com.analytics.reporting_engine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.analytics.reporting_engine.entity.Geolocation;
import com.analytics.reporting_engine.service.GeolocationService;

@RestController
@RequestMapping("/geolocations")
public class GeolocationController {

    private final GeolocationService geolocationService;

    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @PostMapping
    public ResponseEntity<Geolocation> create(
            @RequestBody Geolocation geolocation) {

        return new ResponseEntity<>(
                geolocationService.save(geolocation),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Geolocation>> getAll() {

        return ResponseEntity.ok(
                geolocationService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Geolocation> getById(
            @PathVariable Long id) {

        Geolocation geolocation =
                geolocationService.findById(id);

        if (geolocation == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(geolocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        geolocationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}