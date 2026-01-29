package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportStatusNotAcceptedException;

public record UpdateIssueReportStatusResource(String status) {
    public UpdateIssueReportStatusResource {
        if (status == null) {
            throw new IssueReportStatusNotAcceptedException();
        }
    }
}
