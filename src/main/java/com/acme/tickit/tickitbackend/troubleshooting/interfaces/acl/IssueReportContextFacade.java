package com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ACL for other contexts to query issue reports without depending on the repository directly.
 */
public interface IssueReportContextFacade {

    /**
     * Issue reports assigned to the given user, in the given company, modified between from and to.
     * Used by management context for ItMemberStatistics.
     */
    List<IssueReport> findAssignedToUserModifiedBetween(
            UUID companyId,
            UUID assigneeId,
            LocalDateTime updatedAtFrom,
            LocalDateTime updatedAtTo
    );
}
