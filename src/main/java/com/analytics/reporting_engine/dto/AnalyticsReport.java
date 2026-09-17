package com.analytics.reporting_engine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsReport {

    private Long totalActivities;

    private Long loginCount;

    private Long logoutCount;

    private Long otherActivities;
}