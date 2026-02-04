package com.acme.tickit.tickitbackend.management.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.iam.domain.model.events.UserCreatedEvent;
import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
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

    private final ItMemberStatisticsRepository itMemberStatisticsRepository;

    public UserCreatedEventHandler(ItMemberStatisticsRepository itMemberStatisticsRepository) {
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(UserCreatedEvent event) {
        if (!event.role().requiresItMemberStatistics()) {
            return;
        }
        var statistics = new ItMemberStatistics(event.companyId(), event.userId());
        itMemberStatisticsRepository.save(statistics);
        LOGGER.info("Created ItMemberStatistics for user {} in company {}",
                event.userId(), event.companyId());
    }
}
