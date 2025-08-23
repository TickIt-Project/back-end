package com.acme.tickit.tickitbackend.shared.infrastructure.multitenancy;

import com.acme.tickit.tickitbackend.shared.infrastructure.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public TenantContextFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String tenantId = resolveTenantId(request);
            if (tenantId != null) {
                TenantContext.setCurrentTenantId(UUID.fromString(tenantId));
            }

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolveTenantId(HttpServletRequest request) {
        // 1. Primero intento leer el header explícito
        String tenantHeader = request.getHeader("X-Tenant-Id");
        if (tenantHeader != null && !tenantHeader.isBlank()) {
            return tenantHeader;
        }

        // 2. Si no hay header, intento leerlo del JWT
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtService.extractClaim(token, claims -> claims.get("companyId", String.class));
        }

        return null;
    }
}
