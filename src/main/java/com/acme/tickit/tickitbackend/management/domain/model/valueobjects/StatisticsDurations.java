package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.Duration;

@Embeddable
public record StatisticsDurations(
        Duration openAvgDuration,
        Duration toDoAvgDuration,
        Duration inProgressAvgDuration,
        Duration inReviewAvgDuration,
        Duration inTestingAvgDuration,
        Duration qaRejectedAvgDuration,
        Duration onHoldAvgDuration
) {
    public StatisticsDurations {}
}
