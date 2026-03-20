package com.acme.tickit.tickitbackend.management.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.ItMemberStatisticsResource;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.StatisticTotalsResource;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.StatisticsDurationsResource;

public class ItMemberStatisticsResourceFromEntityAssembler {
    public static ItMemberStatisticsResource toResourceFromEntity(ItMemberStatistics entity) {
        return new ItMemberStatisticsResource(
                entity.getId(),
                entity.getCompanyID() != null ? entity.getCompanyID().companyId() : null,
                entity.getItMemberId() != null ? entity.getItMemberId().itMemberId() : null,
                StatisticTotalsResource.from(entity.getIssuesStatisticTotals()),
                StatisticsDurationsResource.from(entity.getIssuesStatisticDurations()),
                StatisticTotalsResource.from(entity.getCoincidencesStatisticTotals()),
                StatisticsDurationsResource.from(entity.getCoincidencesStatisticDurations()),
                entity.getWeekStartDate()
        );
    }
}
