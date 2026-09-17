package com.analytics.reporting_engine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.analytics.reporting_engine.entity.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    @Override
    @EntityGraph(value = "ActivityLog.details", type = EntityGraph.EntityGraphType.LOAD)
    List<ActivityLog> findAll();
}