package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueCoincidencesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueCoincidenceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface IssueCoincidenceQueryService {
    Optional<IssueCoincidence> handle(GetIssueCoincidenceByIdQuery query);
    List<IssueCoincidence> handle(GetAllIssueCoincidencesQuery query);
}

