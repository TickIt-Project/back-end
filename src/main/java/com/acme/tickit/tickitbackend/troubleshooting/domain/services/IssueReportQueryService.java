package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueReportsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueReportByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IssueReportQueryService {
    Optional<IssueReport> handle(GetIssueReportByIdQuery query);
    List<IssueReport> handle(GetAllIssueReportsQuery query);
}
