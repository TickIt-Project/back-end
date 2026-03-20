package com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.IssueCoincidenceStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueCoincidenceStatisticsRepository extends JpaRepository<IssueCoincidenceStatistics, UUID> {

    Optional<IssueCoincidenceStatistics> findByCompanyID_CompanyIdAndWeekStartDate(UUID companyId, LocalDate weekStartDate);
}
