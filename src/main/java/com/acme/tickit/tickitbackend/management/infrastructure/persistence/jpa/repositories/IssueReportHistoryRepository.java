package com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueReportHistoryRepository extends JpaRepository<IssueReportHistory, UUID> {

    Optional<IssueReportHistory> findFirstByIssueReportId_IssueReportIdAndStateEndTimeIsNullOrderByStateStartTimeDesc(UUID issueReportId);

    List<IssueReportHistory> findByIssueReportId_IssueReportIdIn(Collection<UUID> issueReportIds);
}
