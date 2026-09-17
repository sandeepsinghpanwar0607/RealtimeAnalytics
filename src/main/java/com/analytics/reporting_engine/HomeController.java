package com.analytics.reporting_engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Real-Time Analytics & Reporting Engine is Live!";
    }
}