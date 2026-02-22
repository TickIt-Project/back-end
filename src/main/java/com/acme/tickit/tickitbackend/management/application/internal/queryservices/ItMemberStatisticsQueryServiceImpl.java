package com.acme.tickit.tickitbackend.management.application.internal.queryservices;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.queries.GetAllItMemberStatisticsQuery;
import com.acme.tickit.tickitbackend.management.domain.model.queries.GetItMemberStatisticsByIdQuery;
import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsQueryService;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItMemberStatisticsQueryServiceImpl implements ItMemberStatisticsQueryService {

    private final ItMemberStatisticsRepository itMemberStatisticsRepository;

    public ItMemberStatisticsQueryServiceImpl(ItMemberStatisticsRepository itMemberStatisticsRepository) {
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
    }

    @Override
    public List<ItMemberStatistics> handle(GetAllItMemberStatisticsQuery query) {
        return itMemberStatisticsRepository.findAllByCompanyID(new CompanyID(query.companyId()));
    }

    @Override
    public Optional<ItMemberStatistics> handle(GetItMemberStatisticsByIdQuery query) {
        return itMemberStatisticsRepository.findById(query.id());
    }
}
