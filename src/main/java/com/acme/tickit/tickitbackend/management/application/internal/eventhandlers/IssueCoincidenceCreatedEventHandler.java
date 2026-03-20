package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueCoincidenceHistory;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueCoincidenceHistoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueCoincidenceCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
public class IssueCoincidenceCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueCoincidenceCreatedEventHandler.class);

    private final IssueCoincidenceHistoryRepository issueCoincidenceHistoryRepository;

    public IssueCoincidenceCreatedEventHandler(IssueCoincidenceHistoryRepository issueCoincidenceHistoryRepository) {
        this.issueCoincidenceHistoryRepository = issueCoincidenceHistoryRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(IssueCoincidenceCreatedEvent event) {
        if (!issueCoincidenceHistoryRepository
                .findByIssueCoincidenceId_IssueCoincidenceIdOrderByStateStartTimeAsc(event.issueCoincidenceId())
                .isEmpty()) {
            return;
        }
        IssueCoincidenceHistory first = new IssueCoincidenceHistory(
                "OPEN",
                LocalDateTime.now(),
                event.issueCoincidenceId(),
                null
        );
        issueCoincidenceHistoryRepository.save(first);
        LOGGER.debug("Created first IssueCoincidenceHistory for coincidenceId={}", event.issueCoincidenceId());
    }
}
