package com.acme.tickit.tickitbackend.management.domain.services;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.queries.GetAllItMemberStatisticsByCompanyIdQuery;

import java.util.List;

public interface ItMemberStatisticsQueryService {
    List<ItMemberStatistics> handle(GetAllItMemberStatisticsByCompanyIdQuery query);
}
