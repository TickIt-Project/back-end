package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record StatisticTotals(
        int openTotal,
        int toDoTotal,
        int inProgressTotal,
        int inReviewTotal,
        int inTestingTotal,
        int qaRejectedTotal,
        int onHoldTotal,
        int closedTotal,
        int cancelledTotal
) {
    public StatisticTotals {
        if (openTotal < 0 || toDoTotal < 0 ||
                inProgressTotal < 0 || inTestingTotal < 0 || inReviewTotal < 0 ||
                qaRejectedTotal < 0 || onHoldTotal < 0 ||
                closedTotal < 0 || cancelledTotal < 0
        ) {
            throw new IllegalArgumentException("Counts cannot be negative");
        }
    }
}
