package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportStatusChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IssueReportStatusChangedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportStatusChangedEventHandler.class);

    public IssueReportStatusChangedEventHandler() {
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @org.springframework.transaction.annotation.Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    public void handle(IssueReportStatusChangedEvent event) {
        // ItMemberStatistics are now updated by scheduled job (Saturday midnight) or manual endpoint.
        // No longer updated on status change.
    }
}
