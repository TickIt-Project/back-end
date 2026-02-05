package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportStatusChangedEvent;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IssueReportStatusChangedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportStatusChangedEventHandler.class);

    private final ItMemberStatisticsRepository itMemberStatisticsRepository;

    public IssueReportStatusChangedEventHandler(ItMemberStatisticsRepository itMemberStatisticsRepository) {
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @org.springframework.transaction.annotation.Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    public void handle(IssueReportStatusChangedEvent event) {
        if (event.assigneeId() == null) {
            return;
        }

        if (Status.CLOSED.name().equals(event.newStatus())) {
            itMemberStatisticsRepository
                    .findByCompanyID_CompanyIdAndItMemberId_ItMemberId(event.companyId(), event.assigneeId())
                    .ifPresent(stats -> {
                        stats.IncreaseIssuesResolvedCount();
                        itMemberStatisticsRepository.save(stats);
                        LOGGER.debug("Increased issuesResolvedCount for user {} (issue marked as CLOSED)",
                                event.assigneeId());
                    });
        } else if (Status.CLOSED.name().equals(event.previousStatus())) {
            itMemberStatisticsRepository
                    .findByCompanyID_CompanyIdAndItMemberId_ItMemberId(event.companyId(), event.assigneeId())
                    .ifPresent(stats -> {
                        stats.DecreaseIssuesResolvedCount();
                        itMemberStatisticsRepository.save(stats);
                        LOGGER.debug("Decreased issuesResolvedCount for user {} (issue changed from CLOSED)",
                                event.assigneeId());
                    });
        }
    }
}
