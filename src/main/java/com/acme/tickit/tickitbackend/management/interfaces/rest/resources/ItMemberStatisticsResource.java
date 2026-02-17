package com.acme.tickit.tickitbackend.management.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.UUID;

public record ItMemberStatisticsResource(
        UUID id,
        UUID companyId,
        UUID itMemberId,
        Integer issuesAssignedCount,
        Integer issuesOpenCount,
        Integer issuesInProgressCount,
        Integer issuesOnHoldCount,
        Integer issuesClosedCount,
        Integer issuesCancelledCount,
        LocalDate weekStartDate
) {
}
