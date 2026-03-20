package com.acme.tickit.tickitbackend.management.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticTotals;

public record StatisticTotalsResource(
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
    public static StatisticTotalsResource empty() {
        return new StatisticTotalsResource(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static StatisticTotalsResource from(StatisticTotals t) {
        if (t == null) {
            return empty();
        }
        return new StatisticTotalsResource(
                t.openTotal(), t.toDoTotal(), t.inProgressTotal(), t.inReviewTotal(), t.inTestingTotal(),
                t.qaRejectedTotal(), t.onHoldTotal(), t.closedTotal(), t.cancelledTotal()
        );
    }
}
