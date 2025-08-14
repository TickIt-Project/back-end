package com.acme.tickit.tickitbackend.shared.infrastructure.multitenancy;

import java.util.UUID;

/**
 * Thread-safe context holder for tenant information
 * This class provides a way to store and retrieve tenant information
 * for the current thread/request context across all bounded contexts
 */
public class TenantContext {

    private static final ThreadLocal<UUID> CURRENT_TENANT_ID = new ThreadLocal<>();
    private static final UUID DEFAULT_TENANT_ID = UUID.fromString("63fd9c11-8805-4921-bce3-8e15b13fb69d");

    private TenantContext() {
        // Private constructor to prevent instantiation
    }

    /**
     * Set the tenant ID for current thread
     * @param tenantId the tenant ID
     */
    public static void setCurrentTenantId(UUID tenantId) {
        CURRENT_TENANT_ID.set(tenantId != null ? tenantId : DEFAULT_TENANT_ID);
    }

    /**
     * Get the current tenant ID
     * @return current tenant ID or default if not set
     */
    public static UUID getCurrentTenantId() {
        if (CURRENT_TENANT_ID.get() == null) {
            throw new IllegalStateException("Tenant context not found. Tenant ID must be set before accessing it.");
        }
        return CURRENT_TENANT_ID.get();
    }

    /**
     * Clear the tenant context for the current thread
     * Should be called at the end of request processing
     */
    public static void clear() {
        CURRENT_TENANT_ID.remove();
    }

    /**
     * Check if a tenant is currently set
     * @return true if tenant is set, false otherwise
     */
    public static boolean hasTenant() {
        return CURRENT_TENANT_ID.get() != null;
    }
}