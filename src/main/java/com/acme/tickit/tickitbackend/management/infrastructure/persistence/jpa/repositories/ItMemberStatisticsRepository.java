package com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItMemberStatisticsRepository extends JpaRepository<ItMemberStatistics, UUID> {

    Optional<ItMemberStatistics> findByCompanyID_CompanyIdAndItMemberId_ItMemberId(
            UUID companyId, UUID itMemberId);
}
