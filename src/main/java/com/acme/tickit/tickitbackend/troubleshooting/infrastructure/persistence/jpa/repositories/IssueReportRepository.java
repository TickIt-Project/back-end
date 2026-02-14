package com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.Language;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Severity;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueReportRepository extends JpaRepository<IssueReport, UUID> {
    Optional<IssueReport> findById(UUID id);
    List<IssueReport> findAllByCompanyId(CompanyID companyId);
    boolean existsById(UUID id);

    @Query("""
    SELECT i FROM IssueReport i
    WHERE i.companyId.companyId = :companyId
      AND (:title IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:assigneeId IS NULL OR i.assigneeId.userId = :assigneeId)
      AND (:reporterId IS NULL OR i.reporterId.userId = :reporterId)
      AND (:severity IS NULL OR i.severity = :severity)
      AND (:status IS NULL OR i.status = :status)
      AND (:screenLocationId IS NULL OR i.screenLocation.id = :screenLocationId)
""")
    List<IssueReport> findByFilters(
            UUID companyId,
            String title,
            UUID assigneeId,
            UUID reporterId,
            Severity severity,
            Status status,
            UUID screenLocationId
    );

    List<IssueReport> findByCoincidenceAvailableTrueAndCompanyId_CompanyIdAndIdNot(
            UUID companyId,
            UUID excludeId
    );

    /**
     * Issue reports eligible for coincidence: same company, same language (EN/ES), coincidence available, excluding the given id.
     */
    List<IssueReport> findByCoincidenceAvailableTrueAndCompanyId_CompanyIdAndLanguageAndIdNot(
            UUID companyId,
            Language language,
            UUID excludeId
    );

}
