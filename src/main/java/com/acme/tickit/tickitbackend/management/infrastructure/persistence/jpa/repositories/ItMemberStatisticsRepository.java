package com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItMemberStatisticsRepository extends JpaRepository<ItMemberStatistics, UUID> {

    List<ItMemberStatistics> findAllByCompanyID(CompanyID companyId);

    Optional<ItMemberStatistics> findByCompanyID_CompanyIdAndItMemberId_ItMemberId(
            UUID companyId, UUID itMemberId);

    Optional<ItMemberStatistics> findByCompanyID_CompanyIdAndItMemberId_ItMemberIdAndWeekStartDate(
            UUID companyId, UUID itMemberId, LocalDate weekStartDate);
}
