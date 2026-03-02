package com.acme.tickit.tickitbackend.management.application.internal.scheduling;

import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ItMemberStatisticsScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItMemberStatisticsScheduler.class);

    private final ItMemberStatisticsCommandService itMemberStatisticsCommandService;

    public ItMemberStatisticsScheduler(ItMemberStatisticsCommandService itMemberStatisticsCommandService) {
        this.itMemberStatisticsCommandService = itMemberStatisticsCommandService;
    }

    /** Runs every Saturday at 00:00:00. Full week: Sunday to Saturday midnight. */
    @Scheduled(cron = "0 0 0 * * SAT")
    public void createOrUpdateItMemberStatistics() {
        LOGGER.info("Scheduled: creating/updating ItMemberStatistics for all IT heads and IT members");
        itMemberStatisticsCommandService.createOrUpdateForAllItMembers();
        LOGGER.info("Scheduled: ItMemberStatistics update completed");
    }
}
