package com.acme.tickit.tickitbackend.shared.infrastructure.multitenancy;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TenantContextHelper {

    /**
     * Get current tenant ID for queries and operations
     * @return current tenant ID
     */
    public UUID getCurrentTenantId() {
        return TenantContext.getCurrentTenantId();
    }

    /**
     * Check if the current request has a valid tenant context
     * @return true if tenant context is set, false otherwise
     */
    public boolean hasValidTenantContext() {
        return TenantContext.hasTenant();
    }

    /**
     * Execute a block of code with a specific tenant context
     * Useful for testing or administrative operations
     * @param tenantId tenant ID to use
     * @param operation operation to execute
     * @param <T> return type
     * @return result of the operation
     */
    public <T> T executeInTenantContext(UUID tenantId, TenantOperation<T> operation) {
        // Save current context
        UUID currentTenantId = TenantContext.getCurrentTenantId();

        try {
            // State new context
            TenantContext.setCurrentTenantId(tenantId);
            return operation.execute();
        } finally {
            // Restore original context
            TenantContext.setCurrentTenantId(currentTenantId);
        }
    }

    /**
     * Functional interface for operations that need to run in a specific tenant context
     * @param <T> return type
     */
    @FunctionalInterface
    public interface TenantOperation<T> {
        T execute();
    }

    /**
     * Log current tenant context information (useful for debugging)
     * @return formatted string with tenant information
     */
    public String getTenantContextInfo() {
        return String.format("Current Tenant: " + getCurrentTenantId().toString()
                + " Has Context: " + hasValidTenantContext());
    }
}
