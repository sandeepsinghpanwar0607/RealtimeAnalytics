package com.analytics.reporting_engine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE,
    region = "geolocationCache"
)
@Table(name = "geolocations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    private String region;

    private String latitude;

    private String longitude;
}