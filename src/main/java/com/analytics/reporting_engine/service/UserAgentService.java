package com.analytics.reporting_engine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.analytics.reporting_engine.entity.UserAgent;
import com.analytics.reporting_engine.repository.UserAgentRepository;

@Service
public class UserAgentService {

    private final UserAgentRepository userAgentRepository;

    public UserAgentService(UserAgentRepository userAgentRepository) {
        this.userAgentRepository = userAgentRepository;
    }

    public UserAgent save(UserAgent userAgent) {
        return userAgentRepository.save(userAgent);
    }

    public List<UserAgent> findAll() {
        return userAgentRepository.findAll();
    }

    public UserAgent findById(Long id) {
        return userAgentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userAgentRepository.deleteById(id);
    }
}