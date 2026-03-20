package com.acme.tickit.tickitbackend.management.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticsDurations;

import java.time.Duration;

public record StatisticsDurationsResource(
        long openAvgSeconds,
        long toDoAvgSeconds,
        long inProgressAvgSeconds,
        long inReviewAvgSeconds,
        long inTestingAvgSeconds,
        long qaRejectedAvgSeconds,
        long onHoldAvgSeconds
) {
    public static StatisticsDurationsResource empty() {
        return new StatisticsDurationsResource(0, 0, 0, 0, 0, 0, 0);
    }

    public static StatisticsDurationsResource from(StatisticsDurations d) {
        if (d == null) {
            return empty();
        }
        return new StatisticsDurationsResource(
                sec(d.openAvgDuration()),
                sec(d.toDoAvgDuration()),
                sec(d.inProgressAvgDuration()),
                sec(d.inReviewAvgDuration()),
                sec(d.inTestingAvgDuration()),
                sec(d.qaRejectedAvgDuration()),
                sec(d.onHoldAvgDuration())
        );
    }

    private static long sec(Duration x) {
        return x == null ? 0L : x.getSeconds();
    }
}
