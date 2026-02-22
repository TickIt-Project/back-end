package com.acme.tickit.tickitbackend.management.domain.services;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.queries.GetAllItMemberStatisticsQuery;
import com.acme.tickit.tickitbackend.management.domain.model.queries.GetItMemberStatisticsByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ItMemberStatisticsQueryService {
    List<ItMemberStatistics> handle(GetAllItMemberStatisticsQuery query);
    Optional<ItMemberStatistics> handle(GetItMemberStatisticsByIdQuery query);
}
