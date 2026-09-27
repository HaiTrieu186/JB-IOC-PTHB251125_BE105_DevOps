package com.storex.payment.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();

        try {
            // Dua requestId vao MDC de moi dong log trong request nay deu co chung 1 id
            MDC.put(REQUEST_ID_KEY, requestId);

            // Tra requestId ve cho client de client cung co the doi chieu khi bao loi
            response.setHeader("X-Request-Id", requestId);

            filterChain.doFilter(request, response);
        } finally {
            // Bat buoc phai xoa MDC sau khi xu ly xong, tranh anh huong request khac (do thread bi tai su dung trong pool)
            MDC.remove(REQUEST_ID_KEY);
        }
    }
}
