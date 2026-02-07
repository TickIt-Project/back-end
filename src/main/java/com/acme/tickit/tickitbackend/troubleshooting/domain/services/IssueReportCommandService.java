package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateIssueReportCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportAssigneeCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportImageCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportStatusCommand;

import java.util.Optional;
import java.util.UUID;

public interface IssueReportCommandService {
    UUID handle(CreateIssueReportCommand command);
    Optional<IssueReport> handle(UpdateIssueReportStatusCommand command);
    Optional<IssueReport> handle(UpdateIssueReportAssigneeCommand command);
    Optional<IssueReport> handle(UpdateIssueReportImageCommand command);
}
