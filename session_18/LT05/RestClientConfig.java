package com.storex.order.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    /**
     * QUAN TRONG: phai tao RestTemplate qua RestTemplateBuilder (Spring quan ly)
     * thi Micrometer Tracing moi tu dong gan interceptor de nhet traceId/spanId
     * vao HTTP header khi goi sang service khac (Order Service -> User Service).
     * Neu tu new RestTemplate() thi se KHONG duoc instrument, traceId bi dut doan.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
