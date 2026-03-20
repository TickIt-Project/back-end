package com.acme.tickit.tickitbackend.management.application.internal.statistics;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.management.application.internal.outboundservices.acl.ExternalUserService;
import com.acme.tickit.tickitbackend.management.domain.model.aggregates.IssueCoincidenceStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.aggregates.IssueReportsStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueCoincidenceHistory;
import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueReportHistory;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.ItMemberId;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueCoincidenceHistoryRepository;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueCoincidenceStatisticsRepository;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueReportHistoryRepository;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.IssueReportsStatisticsRepository;
import com.acme.tickit.tickitbackend.management.infrastructure.persistence.jpa.repositories.ItMemberStatisticsRepository;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueCoincidenceRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WeeklyStatisticsOrchestrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeeklyStatisticsOrchestrator.class);

    private final IssueReportRepository issueReportRepository;
    private final IssueCoincidenceRepository issueCoincidenceRepository;
    private final IssueReportHistoryRepository issueReportHistoryRepository;
    private final IssueCoincidenceHistoryRepository issueCoincidenceHistoryRepository;
    private final IssueReportsStatisticsRepository issueReportsStatisticsRepository;
    private final IssueCoincidenceStatisticsRepository issueCoincidenceStatisticsRepository;
    private final ItMemberStatisticsRepository itMemberStatisticsRepository;
    private final ExternalUserService externalUserService;

    public WeeklyStatisticsOrchestrator(
            IssueReportRepository issueReportRepository,
            IssueCoincidenceRepository issueCoincidenceRepository,
            IssueReportHistoryRepository issueReportHistoryRepository,
            IssueCoincidenceHistoryRepository issueCoincidenceHistoryRepository,
            IssueReportsStatisticsRepository issueReportsStatisticsRepository,
            IssueCoincidenceStatisticsRepository issueCoincidenceStatisticsRepository,
            ItMemberStatisticsRepository itMemberStatisticsRepository,
            ExternalUserService externalUserService) {
        this.issueReportRepository = issueReportRepository;
        this.issueCoincidenceRepository = issueCoincidenceRepository;
        this.issueReportHistoryRepository = issueReportHistoryRepository;
        this.issueCoincidenceHistoryRepository = issueCoincidenceHistoryRepository;
        this.issueReportsStatisticsRepository = issueReportsStatisticsRepository;
        this.issueCoincidenceStatisticsRepository = issueCoincidenceStatisticsRepository;
        this.itMemberStatisticsRepository = itMemberStatisticsRepository;
        this.externalUserService = externalUserService;
    }

    private record CompanySnapshot(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> reportHistories,
            List<IssueCoincidence> coincidences,
            Map<UUID, List<IssueCoincidenceHistory>> coincidenceHistories
    ) {
    }

    private CompanySnapshot loadCompanySnapshot(UUID companyId) {
        CompanyID cid = new CompanyID(companyId);
        List<IssueReport> reports = issueReportRepository.findAllByCompanyId(cid);
        List<UUID> reportIds = reports.stream().map(IssueReport::getId).toList();
        List<IssueReportHistory> allHist = reportIds.isEmpty()
                ? List.of()
                : issueReportHistoryRepository.findByIssueReportId_IssueReportIdIn(reportIds);
        Map<UUID, List<IssueReportHistory>> reportHistories =
                ManagementStatisticsEngine.groupIssueReportHistories(allHist);

        List<IssueCoincidence> coincidences = issueCoincidenceRepository.findAllByCompanyID(cid);
        List<UUID> coincidenceIds = coincidences.stream().map(IssueCoincidence::getId).toList();
        List<IssueCoincidenceHistory> allCoinHist = coincidenceIds.isEmpty()
                ? List.of()
                : issueCoincidenceHistoryRepository.findByIssueCoincidenceId_IssueCoincidenceIdIn(coincidenceIds);
        Map<UUID, List<IssueCoincidenceHistory>> coincidenceHistories =
                ManagementStatisticsEngine.groupCoincidenceHistories(allCoinHist);

        return new CompanySnapshot(reports, reportHistories, coincidences, coincidenceHistories);
    }

    @Transactional
    public void runWeeklyStatisticsForAllCompanies(LocalDate scheduledDate, LocalDateTime now) {
        LocalDate weekStart = ManagementStatisticsEngine.sundayOfWeekContaining(scheduledDate);
        Set<UUID> companyIds = new HashSet<>();
        companyIds.addAll(issueReportRepository.findDistinctCompanyIds());
        companyIds.addAll(issueCoincidenceRepository.findDistinctCompanyIds());
        List<User> itMembers = externalUserService.getAllItMembers();

        for (UUID companyId : companyIds) {
            try {
                processCompany(companyId, weekStart, now, itMembers);
            } catch (Exception e) {
                LOGGER.warn("Weekly statistics failed for company {}: {}", companyId, e.getMessage(), e);
            }
        }
    }

    @Transactional
    public UUID recomputeForItMember(UUID userId, LocalDateTime now) {
        UUID companyId = externalUserService.getCompanyIdForItMember(userId).orElse(null);
        if (companyId == null) {
            return null;
        }
        LocalDate weekStart = ManagementStatisticsEngine.sundayOfWeekContaining(now.toLocalDate());
        CompanySnapshot snap = loadCompanySnapshot(companyId);
        ManagementStatisticsEngine.MemberStats memberStats = ManagementStatisticsEngine.computeMemberStats(
                userId, snap.reports(), snap.reportHistories(), snap.coincidences(), snap.coincidenceHistories(),
                weekStart, now);
        saveItMemberStatistics(companyId, userId, weekStart, memberStats);
        return itMemberStatisticsRepository
                .findByCompanyID_CompanyIdAndItMemberId_ItMemberIdAndWeekStartDate(companyId, userId, weekStart)
                .map(ItMemberStatistics::getId)
                .orElse(null);
    }

    private void processCompany(UUID companyId, LocalDate weekStart, LocalDateTime now, List<User> allItMembers) {
        CompanySnapshot snap = loadCompanySnapshot(companyId);

        ManagementStatisticsEngine.IssueReportCompanyStats issueStats =
                ManagementStatisticsEngine.computeIssueReportCompanyStats(
                        snap.reports(), snap.reportHistories(), weekStart, now);
        saveIssueReportsStatistics(companyId, weekStart, issueStats);

        ManagementStatisticsEngine.CoincidenceCompanyStats coinStats =
                ManagementStatisticsEngine.computeCoincidenceCompanyStats(
                        snap.coincidences(), snap.coincidenceHistories(), weekStart, now);
        saveCoincidenceStatistics(companyId, weekStart, coinStats);

        List<User> membersInCompany = allItMembers.stream()
                .filter(u -> companyId.equals(u.getCompany().getId()))
                .collect(Collectors.toList());

        for (User user : membersInCompany) {
            try {
                ManagementStatisticsEngine.MemberStats memberStats = ManagementStatisticsEngine.computeMemberStats(
                        user.getId(), snap.reports(), snap.reportHistories(),
                        snap.coincidences(), snap.coincidenceHistories(), weekStart, now);
                saveItMemberStatistics(companyId, user.getId(), weekStart, memberStats);
            } catch (Exception e) {
                LOGGER.warn("ItMemberStatistics failed user {} company {}: {}", user.getId(), companyId, e.getMessage());
            }
        }
    }

    private void saveIssueReportsStatistics(
            UUID companyId,
            LocalDate weekStart,
            ManagementStatisticsEngine.IssueReportCompanyStats stats
    ) {
        IssueReportsStatistics entity = issueReportsStatisticsRepository
                .findByCompanyID_CompanyIdAndWeekStartDate(companyId, weekStart)
                .orElseGet(IssueReportsStatistics::new);
        entity.setCompanyID(new CompanyID(companyId));
        entity.setStatisticTotals(stats.totals());
        entity.setStatisticDurations(stats.durations());
        entity.setTotalActiveIssues(stats.totalActiveIssues());
        entity.setWeekReportedIssues(stats.weekReportedIssues());
        entity.setWeekStartDate(weekStart);
        issueReportsStatisticsRepository.save(entity);
    }

    private void saveCoincidenceStatistics(
            UUID companyId,
            LocalDate weekStart,
            ManagementStatisticsEngine.CoincidenceCompanyStats stats
    ) {
        IssueCoincidenceStatistics entity = issueCoincidenceStatisticsRepository
                .findByCompanyID_CompanyIdAndWeekStartDate(companyId, weekStart)
                .orElseGet(IssueCoincidenceStatistics::new);
        entity.setCompanyID(new CompanyID(companyId));
        entity.setStatisticTotals(stats.totals());
        entity.setStatisticDurations(stats.durations());
        entity.setWeekDetectedCoincidence(stats.weekDetectedCoincidences());
        entity.setWeekTicketsCreated(stats.weekTicketsCreated());
        entity.setTotalActiveTickets(stats.totalActiveTickets());
        entity.setWeekStartDate(weekStart);
        issueCoincidenceStatisticsRepository.save(entity);
    }

    private void saveItMemberStatistics(
            UUID companyId,
            UUID userId,
            LocalDate weekStart,
            ManagementStatisticsEngine.MemberStats stats
    ) {
        ItMemberStatistics entity = itMemberStatisticsRepository
                .findByCompanyID_CompanyIdAndItMemberId_ItMemberIdAndWeekStartDate(companyId, userId, weekStart)
                .orElseGet(ItMemberStatistics::new);
        entity.setCompanyID(new CompanyID(companyId));
        entity.setItMemberId(new ItMemberId(userId));
        entity.setIssuesStatisticTotals(stats.issuesTotals());
        entity.setIssuesStatisticDurations(stats.issuesDurations());
        entity.setCoincidencesStatisticTotals(stats.coincidencesTotals());
        entity.setCoincidencesStatisticDurations(stats.coincidencesDurations());
        entity.setWeekStartDate(weekStart);
        itMemberStatisticsRepository.save(entity);
    }
}
