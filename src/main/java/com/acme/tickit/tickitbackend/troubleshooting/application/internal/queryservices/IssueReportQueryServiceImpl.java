package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueReportsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueReportByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueReportQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueReportQueryServiceImpl implements IssueReportQueryService {
    private final IssueReportRepository issueReportRepository;

    public IssueReportQueryServiceImpl(IssueReportRepository issueReportRepository) {
        this.issueReportRepository = issueReportRepository;
    }

    @Override
    public Optional<IssueReport> handle(GetIssueReportByIdQuery query) {
        return issueReportRepository.findById(query.issueReportId());
    }

    @Override
    public List<IssueReport> handle(GetAllIssueReportsQuery query) {
        return issueReportRepository.findAllByCompanyId(new CompanyID(query.companyId()));
    }
}
