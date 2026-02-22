package com.acme.tickit.tickitbackend.management.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl.IssueReportContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExternalIssueReportService {

    private final IssueReportContextFacade issueReportContextFacade;

    public ExternalIssueReportService(IssueReportContextFacade issueReportContextFacade) {
        this.issueReportContextFacade = issueReportContextFacade;
    }

    public List<String> findStatusesAssignedToUserModifiedBetween(
            UUID companyId, UUID assigneeId, LocalDateTime updatedAtFrom, LocalDateTime updatedAtTo) {
        return issueReportContextFacade.findStatusesAssignedToUserModifiedBetween(
                companyId, assigneeId, updatedAtFrom, updatedAtTo);
    }
}
