package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportDescriptionNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportReporterIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportSeverityNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportTitleNotAcceptedException;

import java.util.UUID;

public record CreateIssueReportResource(UUID companyId,
                                        String title,
                                        String description,
                                        UUID screenLocationId,
                                        String severity,
                                        String imgUrl,
                                        UUID reporterId,
                                        String issueScreenUrl) {
    public CreateIssueReportResource {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (title == null || title.length() < 2) {
            throw new IssueReportTitleNotAcceptedException();
        }
        if (description == null || description.length() < 30) {
            throw new IssueReportDescriptionNotAcceptedException();
        }
        if (severity == null) {
            throw new IssueReportSeverityNotAcceptedException();
        }
        if (reporterId == null) {
            throw new IssueReportReporterIdNotAcceptedException();
        }
    }
}
