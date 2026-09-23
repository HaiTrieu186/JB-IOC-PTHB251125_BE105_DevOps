package com.example.loggingdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {

    private static final Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @GetMapping("/test-error")
    public String testError() {
        try {
            int result = 10 / 0;
            return "Result: " + result;
        } catch (ArithmeticException e) {
            // Log loi kem Exception -> LogstashEncoder se tu dong dua vao field stack_trace
            logger.error("Loi chia cho 0 khi goi API test-error", e);
            return "Da xay ra loi, xem log de biet chi tiet";
        }
    }
}
