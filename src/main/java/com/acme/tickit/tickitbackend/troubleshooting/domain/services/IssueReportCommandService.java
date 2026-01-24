package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateIssueReportCommand;

import java.util.UUID;

public interface IssueReportCommandService {
    UUID handle(CreateIssueReportCommand command);
}
