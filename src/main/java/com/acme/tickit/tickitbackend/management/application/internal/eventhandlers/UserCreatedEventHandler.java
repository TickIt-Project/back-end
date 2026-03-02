package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.iam.domain.model.events.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserCreatedEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreatedEventHandler.class);

    public UserCreatedEventHandler() {
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(UserCreatedEvent event) {
        // ItMemberStatistics are now created by scheduled job (Saturday midnight) or manual endpoint.
        // No longer created when user is created.
    }
}
