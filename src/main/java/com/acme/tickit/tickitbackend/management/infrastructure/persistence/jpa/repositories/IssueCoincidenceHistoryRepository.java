package com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueCoincidenceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface IssueCoincidenceHistoryRepository extends JpaRepository<IssueCoincidenceHistory, UUID> {

    List<IssueCoincidenceHistory> findByIssueCoincidenceId_IssueCoincidenceIdOrderByStateStartTimeAsc(UUID issueCoincidenceId);

    List<IssueCoincidenceHistory> findByIssueCoincidenceId_IssueCoincidenceIdIn(Collection<UUID> issueCoincidenceIds);
}
