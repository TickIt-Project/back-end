package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportStatusNotAcceptedException;

import java.util.UUID;

public record UpdateIssueReportStatusCommand(UUID issueReportId, String status, String comment) {
    public UpdateIssueReportStatusCommand {
        if (issueReportId == null) {
            throw new IssueReportIdNotAcceptedException();
        }
        if (status == null) {
            throw new IssueReportStatusNotAcceptedException();
        }
    }
}
