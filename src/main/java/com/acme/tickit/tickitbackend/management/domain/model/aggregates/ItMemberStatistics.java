package com.acme.tickit.tickitbackend.management.domain.model.aggregates;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.ItMemberId;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticTotals;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticsDurations;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class ItMemberStatistics extends AuditableAbstractAggregateRoot<ItMemberStatistics> {

    @Embedded
    private CompanyID companyID;

    @Embedded
    private ItMemberId itMemberId;

    @Embedded
    private StatisticTotals issuesStatisticTotals;

    @Embedded
    private StatisticsDurations issuesStatisticDurations;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "openTotal", column = @Column(name = "coincidences_open_total")),
            @AttributeOverride(name = "toDoTotal", column = @Column(name = "coincidences_to_do_total")),
            @AttributeOverride(name = "inProgressTotal", column = @Column(name = "coincidences_in_progress_total")),
            @AttributeOverride(name = "inReviewTotal", column = @Column(name = "coincidences_in_review_total")),
            @AttributeOverride(name = "inTestingTotal", column = @Column(name = "coincidences_in_testing_total")),
            @AttributeOverride(name = "qaRejectedTotal", column = @Column(name = "coincidences_qa_rejected_total")),
            @AttributeOverride(name = "onHoldTotal", column = @Column(name = "coincidences_on_hold_total")),
            @AttributeOverride(name = "closedTotal", column = @Column(name = "coincidences_closed_total")),
            @AttributeOverride(name = "cancelledTotal", column = @Column(name = "coincidences_cancelled_total"))
    })
    private StatisticTotals coincidencesStatisticTotals;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "openAvgDuration", column = @Column(name = "coincidences_open_avg_duration")),
            @AttributeOverride(name = "toDoAvgDuration", column = @Column(name = "coincidences_to_do_avg_duration")),
            @AttributeOverride(name = "inProgressAvgDuration", column = @Column(name = "coincidences_in_progress_avg_duration")),
            @AttributeOverride(name = "inReviewAvgDuration", column = @Column(name = "coincidences_in_review_avg_duration")),
            @AttributeOverride(name = "inTestingAvgDuration", column = @Column(name = "coincidences_in_testing_avg_duration")),
            @AttributeOverride(name = "qaRejectedAvgDuration", column = @Column(name = "coincidences_qa_rejected_avg_duration")),
            @AttributeOverride(name = "onHoldAvgDuration", column = @Column(name = "coincidences_on_hold_avg_duration"))
    })
    private StatisticsDurations coincidencesStatisticDurations;

    private LocalDate weekStartDate;

    public ItMemberStatistics() {}
}
