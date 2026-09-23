package com.example.loggingdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {

    private static final Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @GetMapping("/test-log")
    public String testLog() {
        // Log dong nay se tu dong co them app_name va env trong JSON
        logger.info("Test log co custom fields app_name va env");
        return "Da ghi log, kiem tra console/log file";
    }
}
