package com.acme.tickit.tickitbackend.management.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.management.application.internal.outboundservices.acl.ExternalIssueReportService;
import com.acme.tickit.tickitbackend.management.application.internal.outboundservices.acl.ExternalUserService;
import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.commands.CreateOrUpdateItMemberStatisticsCommand;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueReportCounts;
import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsCommandService;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItMemberStatisticsCommandServiceImpl implements ItMemberStatisticsCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItMemberStatisticsCommandServiceImpl.class);

    private final ExternalUserService externalUserService;
    private final ExternalIssueReportService externalIssueReportService;
    private final ItMemberStatisticsRepository itMemberStatisticsRepository;

    public ItMemberStatisticsCommandServiceImpl(ExternalUserService externalUserService,
                                                ExternalIssueReportService externalIssueReportService,
                                                ItMemberStatisticsRepository itMemberStatisticsRepository) {
        this.externalUserService = externalUserService;
        this.externalIssueReportService = externalIssueReportService;
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
    }

    @Override
    @Transactional
    public UUID handle(CreateOrUpdateItMemberStatisticsCommand command) {
        UUID companyId = externalUserService.getCompanyIdForItMember(command.userId()).orElse(null);
        if (companyId == null) {
            return null;
        }
        LocalDate today = LocalDate.now();
        LocalDate weekStart = getSundayOfWeek(today);
        LocalDateTime updatedAtFrom = weekStart.atStartOfDay();
        LocalDateTime updatedAtTo = LocalDateTime.now();

        var counts = computeCounts(companyId, command.userId(), updatedAtFrom, updatedAtTo);

        return saveOrUpdate(companyId, command.userId(), weekStart, counts).getId();
    }

    @Override
    @Transactional
    public void createOrUpdateForAllItMembers() {
        List<User> itUsers = externalUserService.getAllItMembers();
        LocalDate saturday = LocalDate.now(); // run at Saturday midnight, so "today" is Saturday
        LocalDate weekStart = getSundayOfWeek(saturday);
        LocalDateTime updatedAtFrom = weekStart.atStartOfDay();
        LocalDateTime updatedAtTo = saturday.atTime(LocalTime.MAX); // 23:59:59.999999999

        for (User user : itUsers) {
            try {
                UUID companyId = user.getCompany().getId();
                UUID userId = user.getId();
                var counts = computeCounts(companyId, userId, updatedAtFrom, updatedAtTo);
                saveOrUpdate(companyId, userId, weekStart, counts);
            } catch (Exception e) {
                LOGGER.warn("Failed to create/update ItMemberStatistics for user {}: {}",
                        user.getId(), e.getMessage());
            }
        }
    }

    private ItMemberStatistics saveOrUpdate(UUID companyId, UUID userId, LocalDate weekStart, IssueReportCounts counts) {
        var existing = itMemberStatisticsRepository
                .findByCompanyID_CompanyIdAndItMemberId_ItMemberIdAndWeekStartDate(companyId, userId, weekStart);

        ItMemberStatistics stats;
        if (existing.isPresent()) {
            stats = existing.get();
            stats.setCounts(counts.assigned(), counts.open(), counts.inProgress(), counts.onHold(), counts.closed(), counts.cancelled());
        } else {
            stats = new ItMemberStatistics(companyId, userId, weekStart,
                    counts.assigned(), counts.open(), counts.inProgress(),
                    counts.onHold(), counts.closed(), counts.cancelled());
        }
        return itMemberStatisticsRepository.save(stats);
    }

    private IssueReportCounts computeCounts(UUID companyId, UUID assigneeId, LocalDateTime updatedAtFrom, LocalDateTime updatedAtTo) {
        List<String> statuses = externalIssueReportService.findStatusesAssignedToUserModifiedBetween(
                companyId, assigneeId, updatedAtFrom, updatedAtTo);

        int open = 0, inProgress = 0, onHold = 0, closed = 0, cancelled = 0;
        for (String status : statuses) {
            switch (status) {
                case "OPEN" -> open++;
                case "IN_PROGRESS" -> inProgress++;
                case "ON_HOLD" -> onHold++;
                case "CLOSED" -> closed++;
                case "CANCELLED" -> cancelled++;
                default -> { }
            }
        }
        int assigned = statuses.size();
        return new IssueReportCounts(assigned, open, inProgress, onHold, closed, cancelled);
    }

    /** Returns Sunday of the week containing the given date. (Sunday = start of week) */
    private static LocalDate getSundayOfWeek(LocalDate date) {
        int daysBack = date.getDayOfWeek().getValue() % 7; // SUN=0, MON=1, ..., SAT=6
        return date.minusDays(daysBack);
    }
}
