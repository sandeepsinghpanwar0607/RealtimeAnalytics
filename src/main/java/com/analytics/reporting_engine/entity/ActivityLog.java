package com.analytics.reporting_engine.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedEntityGraph(
    name = "ActivityLog.details",
    attributeNodes = {
        @NamedAttributeNode("user"),
        @NamedAttributeNode("userAgent"),
        @NamedAttributeNode("geolocation")
    }
)
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType;

    private LocalDateTime activityTime;

    private String pageUrl;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_agent_id")
    private UserAgent userAgent;

    @ManyToOne
    @JoinColumn(name = "geolocation_id")
    private Geolocation geolocation;
}