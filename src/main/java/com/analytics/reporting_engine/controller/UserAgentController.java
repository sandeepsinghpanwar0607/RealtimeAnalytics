package com.analytics.reporting_engine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.analytics.reporting_engine.entity.UserAgent;
import com.analytics.reporting_engine.service.UserAgentService;

@RestController
@RequestMapping("/user-agents")
public class UserAgentController {

    private final UserAgentService userAgentService;

    public UserAgentController(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }

    @PostMapping
    public ResponseEntity<UserAgent> create(
            @RequestBody UserAgent userAgent) {

        return new ResponseEntity<>(
                userAgentService.save(userAgent),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<UserAgent>> getAll() {

        return ResponseEntity.ok(
                userAgentService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAgent> getById(
            @PathVariable Long id) {

        UserAgent userAgent = userAgentService.findById(id);

        if (userAgent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userAgent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        userAgentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}