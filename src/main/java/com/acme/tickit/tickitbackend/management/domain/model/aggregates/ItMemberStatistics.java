package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.ItMemberId;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ItMemberStatistics extends AuditableAbstractAggregateRoot<ItMemberStatistics> {

    @Embedded
    private CompanyID companyID;

    @Embedded
    private ItMemberId itMemberId;

    private Integer issuesAssignedCount;
    private Integer issuesResolvedCount;

    private LocalDateTime lastUpdatedDate;

    public ItMemberStatistics() {}

    public ItMemberStatistics(UUID companyID, UUID itMemberId) {
        this.companyID = new CompanyID(companyID);
        this.itMemberId = new ItMemberId(itMemberId);
        this.issuesAssignedCount = 0;
        this.issuesResolvedCount = 0;
        this.lastUpdatedDate = LocalDateTime.now();
    }

    public void updateDate() {
        this.lastUpdatedDate = LocalDateTime.now();
    }

    public void IncreaseIssuesAssignedCount() {
        this.issuesAssignedCount++;
        this.updateDate();
    }

    public void DecreaseIssuesAssignedCount() {
        this.issuesAssignedCount--;
        this.updateDate();
    }

    public void IncreaseIssuesResolvedCount() {
        this.issuesResolvedCount++;
        this.updateDate();
    }

    public void DecreaseIssuesResolvedCount() {
        this.issuesResolvedCount--;
        this.updateDate();
    }
}
