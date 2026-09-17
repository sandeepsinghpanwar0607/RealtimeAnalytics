package com.analytics.reporting_engine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.analytics.reporting_engine.entity.ActivityLog;
import com.analytics.reporting_engine.service.ActivityLogService;

@RestController
@RequestMapping("/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping
    public ResponseEntity<ActivityLog> create(
            @RequestBody ActivityLog activityLog) {

        return new ResponseEntity<>(
                activityLogService.save(activityLog),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ActivityLog>> getAll() {

        return ResponseEntity.ok(
                activityLogService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getById(
            @PathVariable Long id) {

        ActivityLog activityLog =
                activityLogService.findById(id);

        if (activityLog == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(activityLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        activityLogService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}