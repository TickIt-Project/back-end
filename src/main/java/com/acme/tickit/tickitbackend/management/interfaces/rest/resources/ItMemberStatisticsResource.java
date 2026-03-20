package com.acme.tickit.tickitbackend.management.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.UUID;

public record ItMemberStatisticsResource(
        UUID id,
        UUID companyId,
        UUID itMemberId,
        StatisticTotalsResource issuesStatisticTotals,
        StatisticsDurationsResource issuesStatisticDurations,
        StatisticTotalsResource coincidencesStatisticTotals,
        StatisticsDurationsResource coincidencesStatisticDurations,
        LocalDate weekStartDate
) {
}
