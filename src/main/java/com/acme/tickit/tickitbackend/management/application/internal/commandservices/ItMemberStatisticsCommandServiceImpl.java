package com.acme.tickit.tickitbackend.management.application.internal.commandservices;

import com.acme.tickit.tickitbackend.management.application.internal.statistics.WeeklyStatisticsOrchestrator;
import com.acme.tickit.tickitbackend.management.domain.model.commands.CreateOrUpdateItMemberStatisticsCommand;
import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ItMemberStatisticsCommandServiceImpl implements ItMemberStatisticsCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItMemberStatisticsCommandServiceImpl.class);

    private final WeeklyStatisticsOrchestrator weeklyStatisticsOrchestrator;

    public ItMemberStatisticsCommandServiceImpl(WeeklyStatisticsOrchestrator weeklyStatisticsOrchestrator) {
        this.weeklyStatisticsOrchestrator = weeklyStatisticsOrchestrator;
    }

    @Override
    @Transactional
    public UUID handle(CreateOrUpdateItMemberStatisticsCommand command) {
        return weeklyStatisticsOrchestrator.recomputeForItMember(command.userId(), LocalDateTime.now());
    }

    @Override
    @Transactional
    public void createOrUpdateForAllItMembers() {
        LocalDate saturday = LocalDate.now();
        LOGGER.info("Running weekly statistics orchestrator for scheduled date {}", saturday);
        weeklyStatisticsOrchestrator.runWeeklyStatisticsForAllCompanies(saturday, LocalDateTime.now());
        LOGGER.info("Weekly statistics orchestrator completed");
    }
}
