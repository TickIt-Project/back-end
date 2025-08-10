package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class IssueReportsStatistics extends AuditableAbstractAggregateRoot<IssueReportsStatistics> {

    @Embedded
    private CompanyID companyID;

    private Integer totalIssues;
    private Integer openIssues;
    private Integer inProgressIssues;
    private Integer resolvedIssues;

    public IssueReportsStatistics() {}

    public IssueReportsStatistics(UUID companyID, Integer totalIssues, Integer openIssues,
                                  Integer inProgressIssues, Integer resolvedIssues) {
        this.companyID = new CompanyID(companyID);
        this.totalIssues = totalIssues;
        this.openIssues = openIssues;
        this.inProgressIssues = inProgressIssues;
        this.resolvedIssues = resolvedIssues;
    }
}
