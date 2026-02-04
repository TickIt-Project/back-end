package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

public enum Roles {
    IT_HEAD,
    IT_MEMBER,
    EMPLOYEE;

    /**
     * Indicates whether this role requires IT member statistics to be tracked.
     */
    public boolean requiresItMemberStatistics() {
        return this == IT_MEMBER || this == IT_HEAD;
    }
}
