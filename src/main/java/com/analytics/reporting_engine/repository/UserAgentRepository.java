package com.analytics.reporting_engine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.analytics.reporting_engine.entity.UserAgent;

public interface UserAgentRepository extends JpaRepository<UserAgent, Long> {
}