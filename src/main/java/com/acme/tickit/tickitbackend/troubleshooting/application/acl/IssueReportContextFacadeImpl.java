package com.acme.tickit.tickitbackend.troubleshooting.application.acl;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueReportRepository;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl.IssueReportContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class IssueReportContextFacadeImpl implements IssueReportContextFacade {

    private final IssueReportRepository issueReportRepository;

    public IssueReportContextFacadeImpl(IssueReportRepository issueReportRepository) {
        this.issueReportRepository = issueReportRepository;
    }

    @Override
    public List<IssueReport> findAssignedToUserModifiedBetween(
            UUID companyId, UUID assigneeId, LocalDateTime updatedAtFrom, LocalDateTime updatedAtTo) {
        return issueReportRepository.findByCompanyIdAndAssigneeIdAndUpdatedAtBetween(
                companyId, assigneeId, updatedAtFrom, updatedAtTo);
    }
}
