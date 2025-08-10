package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.ItMemberId;
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
public class ItMemberStatistics extends AuditableAbstractAggregateRoot<ItMemberStatistics> {

    @Embedded
    private CompanyID companyID;

    @Embedded
    private ItMemberId itMemberId;

    private Integer issuesAssignedCount;
    private Integer issuesResolvedCount;

    public ItMemberStatistics() {}

    public ItMemberStatistics(UUID companyID, UUID itMemberId,
                              Integer issuesAssignedCount, Integer issuesResolvedCount) {
        this.companyID = new CompanyID(companyID);
        this.itMemberId = new ItMemberId(itMemberId);
        this.issuesAssignedCount = issuesAssignedCount;
        this.issuesResolvedCount = issuesResolvedCount;
    }
}
