package com.analytics.reporting_engine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.reporting_engine.entity.ActivityLog;
import com.analytics.reporting_engine.exception.ResourceNotFoundException;
import com.analytics.reporting_engine.repository.ActivityLogRepository;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public ActivityLog save(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> findAll() {
        return activityLogRepository.findAll();
    }
    public ActivityLog findById(Long id) {

        return activityLogRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Activity Log not found with id: " + id
                    )
                );
    }
    public void deleteById(Long id) {
        activityLogRepository.deleteById(id);
    }
}