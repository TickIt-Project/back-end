package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticTotals;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticsDurations;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class IssueCoincidenceStatistics extends AuditableAbstractAggregateRoot<IssueCoincidenceStatistics> {

    @Embedded
    private CompanyID companyID;

    @Embedded
    private StatisticTotals statisticTotals;

    @Embedded
    private StatisticsDurations statisticDurations;

    private Integer weekDetectedCoincidence;
    private Integer weekTicketsCreated;
    private Integer totalActiveTickets;

    private LocalDate weekStartDate;

    public IssueCoincidenceStatistics() {}
}
