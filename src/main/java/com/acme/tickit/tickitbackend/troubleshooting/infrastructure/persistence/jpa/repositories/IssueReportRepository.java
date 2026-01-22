package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueReportRepository extends JpaRepository<IssueReport, UUID> {
    Optional<IssueReport> findById(UUID id);
    List<IssueReport> findAllByCompanyId(UUID companyId);
}
