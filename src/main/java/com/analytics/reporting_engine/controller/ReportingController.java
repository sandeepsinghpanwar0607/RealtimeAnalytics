package com.analytics.reporting_engine.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.reporting_engine.dto.ActivityLogResponseDTO;
import com.analytics.reporting_engine.dto.AnalyticsReport;
import com.analytics.reporting_engine.service.ReportingService;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/analytics")
    public AnalyticsReport getAnalyticsReport() {

        return reportingService.getAnalyticsReport();
    }

    @GetMapping("/activities")
    public List<ActivityLogResponseDTO> getActivities(

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate,

            @RequestParam(required = false)
            String actionType,

            @RequestParam(required = false)
            Long userId,

            @RequestParam(required = false)
            String gender,

            @RequestParam(required = false)
            Integer age,

            @RequestParam(required = false)
            String country,

            @RequestParam(required = false)
            String city,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size,

            @RequestParam(defaultValue = "activityTime")
            String sortBy) {

        return reportingService.getReport(
                fromDate,
                toDate,
                actionType,
                userId,
                gender,
                age,
                country,
                city,
                page,
                size,
                sortBy
        );
    }
}