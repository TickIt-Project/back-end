package com.acme.tickit.tickitbackend.management.application.internal.scheduling;

import com.acme.tickit.tickitbackend.management.application.internal.statistics.WeeklyStatisticsOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ItMemberStatisticsScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItMemberStatisticsScheduler.class);

    private final WeeklyStatisticsOrchestrator weeklyStatisticsOrchestrator;

    public ItMemberStatisticsScheduler(WeeklyStatisticsOrchestrator weeklyStatisticsOrchestrator) {
        this.weeklyStatisticsOrchestrator = weeklyStatisticsOrchestrator;
    }

    /** Runs every Sunday at 00:00:00 (Saturday night -> Sunday) for the week that just closed. */
    @Scheduled(cron = "0 0 0 * * SUN")
    public void createOrUpdateItMemberStatistics() {
        LOGGER.info("Scheduled: weekly statistics (issue reports, coincidences, IT members)");
        // At Sunday 00:00, use Saturday's date so the orchestrator computes the week that ended minutes ago.
        weeklyStatisticsOrchestrator.runWeeklyStatisticsForAllCompanies(LocalDate.now().minusDays(1), LocalDateTime.now());
        LOGGER.info("Scheduled: weekly statistics completed");
    }
}
