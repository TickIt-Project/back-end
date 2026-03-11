package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueReportHistory;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueStatus;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueReportHistoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
public class IssueReportCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportCreatedEventHandler.class);

    private static final String INITIAL_STATUS = IssueStatus.OPEN.name();

    private final IssueReportHistoryRepository issueReportHistoryRepository;

    public IssueReportCreatedEventHandler(IssueReportHistoryRepository issueReportHistoryRepository) {
        this.issueReportHistoryRepository = issueReportHistoryRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(IssueReportCreatedEvent event) {
        IssueReportHistory firstHistory = new IssueReportHistory(
                INITIAL_STATUS,
                LocalDateTime.now(),
                event.issueReportId(),
                null
        );
        issueReportHistoryRepository.save(firstHistory);
        LOGGER.debug("Created first IssueReportHistory for issueReportId={} status={}", event.issueReportId(), INITIAL_STATUS);
    }
}
