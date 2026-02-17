package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.ItMemberId;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private Integer issuesOpenCount;
    private Integer issuesInProgressCount;
    private Integer issuesOnHoldCount;
    private Integer issuesClosedCount;
    private Integer issuesCancelledCount;

    /** Sunday of the week this stats covers. Null for legacy records created before week-based logic. */
    private LocalDate weekStartDate;

    public ItMemberStatistics() {}

    public ItMemberStatistics(UUID companyID, UUID itMemberId, LocalDate weekStartDate,
                              int issuesAssignedCount, int issuesOpenCount, int issuesInProgressCount,
                              int issuesOnHoldCount, int issuesClosedCount, int issuesCancelledCount) {
        this.companyID = new CompanyID(companyID);
        this.itMemberId = new ItMemberId(itMemberId);
        this.weekStartDate = weekStartDate;
        this.issuesAssignedCount = issuesAssignedCount;
        this.issuesOpenCount = issuesOpenCount;
        this.issuesInProgressCount = issuesInProgressCount;
        this.issuesOnHoldCount = issuesOnHoldCount;
        this.issuesClosedCount = issuesClosedCount;
        this.issuesCancelledCount = issuesCancelledCount;
    }

    public void setCounts(int issuesAssignedCount, int issuesOpenCount, int issuesInProgressCount,
                          int issuesOnHoldCount, int issuesClosedCount, int issuesCancelledCount) {
        this.issuesAssignedCount = issuesAssignedCount;
        this.issuesOpenCount = issuesOpenCount;
        this.issuesInProgressCount = issuesInProgressCount;
        this.issuesOnHoldCount = issuesOnHoldCount;
        this.issuesClosedCount = issuesClosedCount;
        this.issuesCancelledCount = issuesCancelledCount;
    }
}
