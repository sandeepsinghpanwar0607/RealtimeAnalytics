package com.analytics.reporting_engine.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogResponseDTO {

    private Long id;

    private String actionType;

    private LocalDateTime activityTime;

    private String pageUrl;

    private String ipAddress;

    private String userName;

    private String userEmail;

    private String gender;

    private Integer age;

    private String country;

    private String city;

    private String browser;

    private String browserVersion;

    private String operatingSystem;

    private String deviceType;

    private String geoCountry;

    private String geoCity;

    private String region;

    private String latitude;

    private String longitude;
}