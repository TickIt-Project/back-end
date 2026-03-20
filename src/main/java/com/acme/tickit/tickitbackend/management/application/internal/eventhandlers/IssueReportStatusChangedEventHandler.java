package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueReportHistory;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueReportHistoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportStatusChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
public class IssueReportStatusChangedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportStatusChangedEventHandler.class);

    private final IssueReportHistoryRepository issueReportHistoryRepository;

    public IssueReportStatusChangedEventHandler(IssueReportHistoryRepository issueReportHistoryRepository) {
        this.issueReportHistoryRepository = issueReportHistoryRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(IssueReportStatusChangedEvent event) {
        LocalDateTime now = LocalDateTime.now();
        issueReportHistoryRepository
                .findFirstByIssueReportId_IssueReportIdAndStateEndTimeIsNullOrderByStateStartTimeDesc(event.issueReportId())
                .ifPresent(previous -> {
                    previous.closeState(now);
                    issueReportHistoryRepository.save(previous);
                });
        IssueReportHistory newHistory = new IssueReportHistory(
                event.newStatus(),
                now,
                event.issueReportId(),
                event.comment()
        );
        issueReportHistoryRepository.save(newHistory);
        LOGGER.debug("Created IssueReportHistory for issueReportId={} status={}", event.issueReportId(), event.newStatus());
    }
}
