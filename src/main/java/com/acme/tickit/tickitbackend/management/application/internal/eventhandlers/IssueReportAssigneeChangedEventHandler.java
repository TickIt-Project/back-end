package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportAssigneeChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IssueReportAssigneeChangedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportAssigneeChangedEventHandler.class);

    private final ItMemberStatisticsRepository itMemberStatisticsRepository;

    public IssueReportAssigneeChangedEventHandler(ItMemberStatisticsRepository itMemberStatisticsRepository) {
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @org.springframework.transaction.annotation.Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    public void handle(IssueReportAssigneeChangedEvent event) {
        if (event.previousAssigneeId() != null) {
            itMemberStatisticsRepository
                    .findByCompanyID_CompanyIdAndItMemberId_ItMemberId(event.companyId(), event.previousAssigneeId())
                    .ifPresent(stats -> {
                        stats.DecreaseIssuesAssignedCount();
                        itMemberStatisticsRepository.save(stats);
                        LOGGER.debug("Decreased issuesAssignedCount for user {} (previous assignee)",
                                event.previousAssigneeId());
                    });
        }
        itMemberStatisticsRepository
                .findByCompanyID_CompanyIdAndItMemberId_ItMemberId(event.companyId(), event.newAssigneeId())
                .ifPresent(stats -> {
                    stats.IncreaseIssuesAssignedCount();
                    itMemberStatisticsRepository.save(stats);
                    LOGGER.debug("Increased issuesAssignedCount for user {} (new assignee)", event.newAssigneeId());
                });
    }
}
