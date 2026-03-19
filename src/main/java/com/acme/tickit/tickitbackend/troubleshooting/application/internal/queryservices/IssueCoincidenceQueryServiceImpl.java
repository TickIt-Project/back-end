package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueCoincidencesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueCoincidenceByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueCoincidenceQueryService;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueCoincidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueCoincidenceQueryServiceImpl implements IssueCoincidenceQueryService {
    private final IssueCoincidenceRepository issueCoincidenceRepository;

    public IssueCoincidenceQueryServiceImpl(IssueCoincidenceRepository issueCoincidenceRepository) {
        this.issueCoincidenceRepository = issueCoincidenceRepository;
    }

    @Override
    public Optional<IssueCoincidence> handle(GetIssueCoincidenceByIdQuery query) {
        return issueCoincidenceRepository.findById(query.issueCoincidenceId());
    }

    @Override
    public List<IssueCoincidence> handle(GetAllIssueCoincidencesQuery query) {
        return issueCoincidenceRepository.findAllByCompanyID(new CompanyID(query.companyId()));
    }
}

