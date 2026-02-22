package com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ACL for other contexts to query issue report data without depending on the aggregate directly.
 */
public interface IssueReportContextFacade {

    /**
     * Status names of issue reports assigned to the given user, in the given company, modified between from and to.
     * Used by management context for ItMemberStatistics (counts by status).
     */
    List<String> findStatusesAssignedToUserModifiedBetween(
            UUID companyId,
            UUID assigneeId,
            LocalDateTime updatedAtFrom,
            LocalDateTime updatedAtTo
    );
}
