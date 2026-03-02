package com.acme.tickit.tickitbackend.management.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.ItMemberStatisticsResource;

public class ItMemberStatisticsResourceFromEntityAssembler {
    public static ItMemberStatisticsResource toResourceFromEntity(ItMemberStatistics entity) {
        return new ItMemberStatisticsResource(
                entity.getId(),
                entity.getCompanyID() != null ? entity.getCompanyID().companyId() : null,
                entity.getItMemberId() != null ? entity.getItMemberId().itMemberId() : null,
                entity.getIssuesAssignedCount() != null ? entity.getIssuesAssignedCount() : 0,
                entity.getIssuesOpenCount() != null ? entity.getIssuesOpenCount() : 0,
                entity.getIssuesInProgressCount() != null ? entity.getIssuesInProgressCount() : 0,
                entity.getIssuesOnHoldCount() != null ? entity.getIssuesOnHoldCount() : 0,
                entity.getIssuesClosedCount() != null ? entity.getIssuesClosedCount() : 0,
                entity.getIssuesCancelledCount() != null ? entity.getIssuesCancelledCount() : 0,
                entity.getWeekStartDate()
        );
    }
}
