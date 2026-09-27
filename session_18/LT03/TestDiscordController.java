package com.storex.payment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDiscordController {

    private static final Logger log = LoggerFactory.getLogger(TestDiscordController.class);

    @GetMapping("/test-discord")
    public String testDiscord() {
        try {
            throw new RuntimeException("Loi ket noi Database");
        } catch (RuntimeException e) {
            log.error("Loi DB", e);
        }
        return "Da ban log ERROR, kiem tra kenh Discord";
    }
}
