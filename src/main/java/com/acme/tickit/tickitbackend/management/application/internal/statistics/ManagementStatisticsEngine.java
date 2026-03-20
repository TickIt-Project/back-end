package com.acme.tickit.tickitbackend.management.application.internal.statistics;

import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueCoincidenceHistory;
import com.acme.tickit.tickitbackend.management.domain.model.entities.IssueReportHistory;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueStatus;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticTotals;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.StatisticsDurations;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Totals + average durations for issue reports / coincidences per business rules (Saturday batch).
 * <p>
 * Pipeline averages (Open → To Do → In Progress → In Review → In Testing → QA Rejected → Closed):
 * a ticket contributes to the average duration of state S only if its current status is strictly after S
 * on that ladder. ON_HOLD skips this filter; CANCELLED is excluded from durations entirely.
 */
public final class ManagementStatisticsEngine {

    /**
     * Main pipeline order for duration averages (strict): a ticket contributes to the average of state S
     * only if its current status rank is strictly greater than S's rank. ON_HOLD and CANCELLED are not on
     * this ladder; CANCELLED is excluded from durations; ON_HOLD skips this filter (rule does not apply).
     */
    private static final int PIPELINE_RANK_NONE = -1;

    private ManagementStatisticsEngine() {
    }

    private static int pipelineRank(IssueStatus s) {
        if (s == null) {
            return PIPELINE_RANK_NONE;
        }
        return switch (s) {
            case OPEN -> 0;
            case TO_DO -> 1;
            case IN_PROGRESS -> 2;
            case IN_REVIEW -> 3;
            case IN_TESTING -> 4;
            case QA_REJECTED -> 5;
            case CLOSED -> 6;
            default -> PIPELINE_RANK_NONE;
        };
    }

    private static int pipelineRank(Status s) {
        return switch (s) {
            case OPEN -> 0;
            case TO_DO -> 1;
            case IN_PROGRESS -> 2;
            case IN_REVIEW -> 3;
            case IN_TESTING -> 4;
            case QA_REJECTED -> 5;
            case CLOSED -> 6;
            default -> PIPELINE_RANK_NONE;
        };
    }

    /** Include duration for pipeline state {@code stateRank} only if current issue status is strictly past it (or ON_HOLD: no filter). */
    private static boolean includeIssueReportInPipelineAverage(Status current, int stateRank) {
        if (current == Status.ON_HOLD) {
            return true;
        }
        int inc = pipelineRank(current);
        if (inc == PIPELINE_RANK_NONE) {
            return false;
        }
        return inc > stateRank;
    }

    private static IssueStatus coincidenceStatusOrOpen(IssueCoincidence c) {
        IssueStatus st = parseCoincidenceStatus(c.getStatus());
        return st != null ? st : IssueStatus.OPEN;
    }

    private static boolean includeCoincidenceInPipelineAverage(IssueCoincidence c, int stateRank) {
        IssueStatus cur = coincidenceStatusOrOpen(c);
        if (cur == IssueStatus.ON_HOLD) {
            return true;
        }
        int inc = pipelineRank(cur);
        if (inc == PIPELINE_RANK_NONE) {
            return false;
        }
        return inc > stateRank;
    }

    /** Sunday 00:00 of the calendar week that contains {@code date} (week runs Sun–Sat). */
    public static LocalDate sundayOfWeekContaining(LocalDate date) {
        int daysBack = date.getDayOfWeek().getValue() % 7;
        return date.minusDays(daysBack);
    }

    public static Map<UUID, List<IssueReportHistory>> groupIssueReportHistories(List<IssueReportHistory> rows) {
        Map<UUID, List<IssueReportHistory>> map = new HashMap<>();
        for (IssueReportHistory h : rows) {
            UUID id = h.getIssueReportId().issueReportId();
            map.computeIfAbsent(id, k -> new ArrayList<>()).add(h);
        }
        for (List<IssueReportHistory> list : map.values()) {
            list.sort(Comparator.comparing(IssueReportHistory::getStateStartTime));
        }
        return map;
    }

    public static Map<UUID, List<IssueCoincidenceHistory>> groupCoincidenceHistories(List<IssueCoincidenceHistory> rows) {
        Map<UUID, List<IssueCoincidenceHistory>> map = new HashMap<>();
        for (IssueCoincidenceHistory h : rows) {
            UUID id = h.getIssueCoincidenceId().issueCoincidenceId();
            map.computeIfAbsent(id, k -> new ArrayList<>()).add(h);
        }
        for (List<IssueCoincidenceHistory> list : map.values()) {
            list.sort(Comparator.comparing(IssueCoincidenceHistory::getStateStartTime));
        }
        return map;
    }

    public record IssueReportCompanyStats(
            StatisticTotals totals,
            StatisticsDurations durations,
            int totalActiveIssues,
            int weekReportedIssues
    ) {
    }

    public record CoincidenceCompanyStats(
            StatisticTotals totals,
            StatisticsDurations durations,
            int weekDetectedCoincidences,
            int weekTicketsCreated,
            int totalActiveTickets
    ) {
    }

    public record MemberStats(
            StatisticTotals issuesTotals,
            StatisticsDurations issuesDurations,
            StatisticTotals coincidencesTotals,
            StatisticsDurations coincidencesDurations
    ) {
    }

    public static IssueReportCompanyStats computeIssueReportCompanyStats(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            LocalDate weekStartDate,
            LocalDateTime now
    ) {
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEndExclusive = weekStart.plusWeeks(1);

        int weekReported = (int) reports.stream()
                .filter(r -> !r.getCreatedAt().isBefore(weekStart) && r.getCreatedAt().isBefore(weekEndExclusive))
                .count();

        int totalActive = (int) reports.stream()
                .filter(r -> r.getStatus() != Status.CLOSED && r.getStatus() != Status.CANCELLED)
                .count();

        StatisticTotals totals = buildIssueReportTotals(reports, historiesByReportId, weekStart);
        StatisticsDurations durations = buildIssueReportDurations(reports, historiesByReportId, now);

        return new IssueReportCompanyStats(totals, durations, totalActive, weekReported);
    }

    private static StatisticTotals buildIssueReportTotals(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            LocalDateTime weekBoundaryStart
    ) {
        int open = 0, toDo = 0, inProgress = 0, inReview = 0, inTesting = 0, qa = 0, onHold = 0, closed = 0, cancelled = 0;

        for (IssueReport r : reports) {
            List<IssueReportHistory> h = historiesByReportId.getOrDefault(r.getId(), List.of());
            Status st = r.getStatus();

            if (st == Status.CLOSED) {
                if (qualifiesSinceBoundary(h, IssueStatus.CLOSED, weekBoundaryStart)) {
                    closed++;
                }
            } else if (st == Status.CANCELLED) {
                if (qualifiesSinceBoundary(h, IssueStatus.CANCELLED, weekBoundaryStart)) {
                    cancelled++;
                }
            } else {
                switch (st) {
                    case OPEN -> open++;
                    case TO_DO -> toDo++;
                    case IN_PROGRESS -> inProgress++;
                    case IN_REVIEW -> inReview++;
                    case IN_TESTING -> inTesting++;
                    case QA_REJECTED -> qa++;
                    case ON_HOLD -> onHold++;
                    default -> {
                    }
                }
            }
        }
        return new StatisticTotals(open, toDo, inProgress, inReview, inTesting, qa, onHold, closed, cancelled);
    }

    private static boolean qualifiesSinceBoundary(List<IssueReportHistory> sorted, IssueStatus status, LocalDateTime boundary) {
        return lastSegmentStart(sorted, status).map(t -> !t.isBefore(boundary)).orElse(false);
    }

    private static Optional<LocalDateTime> lastSegmentStart(List<IssueReportHistory> sorted, IssueStatus status) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .map(IssueReportHistory::getStateStartTime)
                .max(Comparator.naturalOrder());
    }

    private static StatisticsDurations buildIssueReportDurations(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            LocalDateTime now
    ) {
        List<IssueReport> nonCancelled = reports.stream()
                .filter(r -> r.getStatus() != Status.CANCELLED)
                .collect(Collectors.toList());

        Duration open = avgWallFirstToLast(nonCancelled, historiesByReportId, IssueStatus.OPEN, now);
        Duration toDo = avgLastSegmentOnly(nonCancelled, historiesByReportId, IssueStatus.TO_DO, now);
        Duration inProgress = avgWallFirstToLast(nonCancelled, historiesByReportId, IssueStatus.IN_PROGRESS, now);
        Duration inReview = avgLastSegmentOnly(nonCancelled, historiesByReportId, IssueStatus.IN_REVIEW, now);
        Duration inTesting = avgLastSegmentOnly(nonCancelled, historiesByReportId, IssueStatus.IN_TESTING, now);
        Duration qa = avgQaRejected(nonCancelled, historiesByReportId, now);
        Duration onHold = avgOnHold(nonCancelled, historiesByReportId, now);

        return new StatisticsDurations(open, toDo, inProgress, inReview, inTesting, qa, onHold);
    }

    private static Duration avgWallFirstToLast(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            IssueStatus status,
            LocalDateTime now
    ) {
        int stateRank = pipelineRank(status);
        long sum = 0;
        int n = 0;
        for (IssueReport r : reports) {
            if (!includeIssueReportInPipelineAverage(r.getStatus(), stateRank)) {
                continue;
            }
            List<IssueReportHistory> h = historiesByReportId.getOrDefault(r.getId(), List.of());
            Duration d = wallClockFirstToLast(h, status, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgLastSegmentOnly(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            IssueStatus status,
            LocalDateTime now
    ) {
        int stateRank = pipelineRank(status);
        long sum = 0;
        int n = 0;
        for (IssueReport r : reports) {
            if (!includeIssueReportInPipelineAverage(r.getStatus(), stateRank)) {
                continue;
            }
            List<IssueReportHistory> h = historiesByReportId.getOrDefault(r.getId(), List.of());
            Duration d = lastSegmentDuration(h, status, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgQaRejected(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            LocalDateTime now
    ) {
        int qaRank = pipelineRank(IssueStatus.QA_REJECTED);
        long sum = 0;
        int n = 0;
        for (IssueReport r : reports) {
            List<IssueReportHistory> h = historiesByReportId.getOrDefault(r.getId(), List.of());
            if (!hasStatus(h, IssueStatus.QA_REJECTED) && r.getStatus() != Status.CLOSED) {
                continue;
            }
            if (!includeIssueReportInPipelineAverage(r.getStatus(), qaRank)) {
                continue;
            }
            Duration d = wallClockFirstToLast(h, IssueStatus.QA_REJECTED, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgOnHold(
            List<IssueReport> reports,
            Map<UUID, List<IssueReportHistory>> historiesByReportId,
            LocalDateTime now
    ) {
        long sum = 0;
        int n = 0;
        for (IssueReport r : reports) {
            List<IssueReportHistory> h = historiesByReportId.getOrDefault(r.getId(), List.of());
            if (!hasStatus(h, IssueStatus.ON_HOLD) && r.getStatus() != Status.CLOSED) {
                continue;
            }
            Duration d = sumSegmentDurations(h, IssueStatus.ON_HOLD, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static boolean hasStatus(List<IssueReportHistory> sorted, IssueStatus status) {
        return sorted.stream().anyMatch(x -> x.getIssueStatus() == status);
    }

    private static LocalDateTime endOrNow(IssueReportHistory h, LocalDateTime now) {
        return h.getStateEndTime() != null ? h.getStateEndTime() : now;
    }

    private static Duration wallClockFirstToLast(List<IssueReportHistory> sorted, IssueStatus status, LocalDateTime now) {
        List<IssueReportHistory> segs = sorted.stream().filter(x -> x.getIssueStatus() == status).toList();
        if (segs.isEmpty()) {
            return Duration.ZERO;
        }
        LocalDateTime start = segs.stream().map(IssueReportHistory::getStateStartTime).min(Comparator.naturalOrder()).orElseThrow();
        LocalDateTime end = segs.stream().map(h -> endOrNow(h, now)).max(Comparator.naturalOrder()).orElseThrow();
        return Duration.between(start, end);
    }

    private static Duration lastSegmentDuration(List<IssueReportHistory> sorted, IssueStatus status, LocalDateTime now) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .max(Comparator.comparing(IssueReportHistory::getStateStartTime))
                .map(h -> Duration.between(h.getStateStartTime(), endOrNow(h, now)))
                .orElse(Duration.ZERO);
    }

    private static Duration sumSegmentDurations(List<IssueReportHistory> sorted, IssueStatus status, LocalDateTime now) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .map(h -> Duration.between(h.getStateStartTime(), endOrNow(h, now)))
                .reduce(Duration.ZERO, Duration::plus);
    }

    // --- Coincidences (only jiraSynced == true for totals/durations; week counters separate) ---

    public static CoincidenceCompanyStats computeCoincidenceCompanyStats(
            List<IssueCoincidence> coincidences,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            LocalDate weekStartDate,
            LocalDateTime now
    ) {
        LocalDateTime weekStart = weekStartDate.atStartOfDay();
        LocalDateTime weekEndExclusive = weekStart.plusWeeks(1);

        int weekDetected = (int) coincidences.stream()
                .filter(c -> !c.getCreatedAt().isBefore(weekStart) && c.getCreatedAt().isBefore(weekEndExclusive))
                .count();

        int weekTickets = (int) coincidences.stream()
                .filter(c -> Boolean.TRUE.equals(c.getJiraSynced())
                        && !c.getCreatedAt().isBefore(weekStart)
                        && c.getCreatedAt().isBefore(weekEndExclusive))
                .count();

        int totalActiveTickets = (int) coincidences.stream()
                .filter(c -> Boolean.TRUE.equals(c.getJiraSynced()) && !isClosedOrCancelledCoincidence(c))
                .count();

        List<IssueCoincidence> synced = coincidences.stream()
                .filter(c -> Boolean.TRUE.equals(c.getJiraSynced()))
                .collect(Collectors.toList());

        StatisticTotals totals = buildCoincidenceTotals(synced, historiesById, weekStart);
        StatisticsDurations durations = buildCoincidenceDurations(synced, historiesById, now);

        return new CoincidenceCompanyStats(totals, durations, weekDetected, weekTickets, totalActiveTickets);
    }

    private static boolean isClosedOrCancelledCoincidence(IssueCoincidence c) {
        IssueStatus st = parseCoincidenceStatus(c.getStatus());
        return st == IssueStatus.CLOSED || st == IssueStatus.CANCELLED;
    }

    private static IssueStatus parseCoincidenceStatus(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return IssueStatus.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static StatisticTotals buildCoincidenceTotals(
            List<IssueCoincidence> synced,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            LocalDateTime weekBoundaryStart
    ) {
        int open = 0, toDo = 0, inProgress = 0, inReview = 0, inTesting = 0, qa = 0, onHold = 0, closed = 0, cancelled = 0;

        for (IssueCoincidence c : synced) {
            List<IssueCoincidenceHistory> h = historiesById.getOrDefault(c.getId(), List.of());
            IssueStatus st = parseCoincidenceStatus(c.getStatus());
            if (st == null) {
                open++;
                continue;
            }
            if (st == IssueStatus.CLOSED) {
                if (qualifiesCoincidenceSinceBoundary(h, IssueStatus.CLOSED, weekBoundaryStart)) {
                    closed++;
                }
            } else if (st == IssueStatus.CANCELLED) {
                if (qualifiesCoincidenceSinceBoundary(h, IssueStatus.CANCELLED, weekBoundaryStart)) {
                    cancelled++;
                }
            } else {
                switch (st) {
                    case OPEN -> open++;
                    case TO_DO -> toDo++;
                    case IN_PROGRESS -> inProgress++;
                    case IN_REVIEW -> inReview++;
                    case IN_TESTING -> inTesting++;
                    case QA_REJECTED -> qa++;
                    case ON_HOLD -> onHold++;
                    default -> {
                    }
                }
            }
        }
        return new StatisticTotals(open, toDo, inProgress, inReview, inTesting, qa, onHold, closed, cancelled);
    }

    private static boolean qualifiesCoincidenceSinceBoundary(
            List<IssueCoincidenceHistory> sorted,
            IssueStatus status,
            LocalDateTime boundary
    ) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .map(IssueCoincidenceHistory::getStateStartTime)
                .max(Comparator.naturalOrder())
                .map(t -> !t.isBefore(boundary))
                .orElse(false);
    }

    private static LocalDateTime endOrNow(IssueCoincidenceHistory h, LocalDateTime now) {
        return h.getStateEndTime() != null ? h.getStateEndTime() : now;
    }

    private static StatisticsDurations buildCoincidenceDurations(
            List<IssueCoincidence> synced,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            LocalDateTime now
    ) {
        List<IssueCoincidence> nonCancelled = synced.stream()
                .filter(c -> parseCoincidenceStatus(c.getStatus()) != IssueStatus.CANCELLED)
                .collect(Collectors.toList());

        Duration open = avgCoincidenceWall(nonCancelled, historiesById, IssueStatus.OPEN, now);
        Duration toDo = avgCoincidenceLast(nonCancelled, historiesById, IssueStatus.TO_DO, now);
        Duration inProgress = avgCoincidenceWall(nonCancelled, historiesById, IssueStatus.IN_PROGRESS, now);
        Duration inReview = avgCoincidenceLast(nonCancelled, historiesById, IssueStatus.IN_REVIEW, now);
        Duration inTesting = avgCoincidenceLast(nonCancelled, historiesById, IssueStatus.IN_TESTING, now);
        Duration qa = avgCoincidenceQa(nonCancelled, historiesById, now);
        Duration onHold = avgCoincidenceOnHold(nonCancelled, historiesById, now);

        return new StatisticsDurations(open, toDo, inProgress, inReview, inTesting, qa, onHold);
    }

    private static Duration avgCoincidenceWall(
            List<IssueCoincidence> list,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            IssueStatus status,
            LocalDateTime now
    ) {
        int stateRank = pipelineRank(status);
        long sum = 0;
        int n = 0;
        for (IssueCoincidence c : list) {
            if (!includeCoincidenceInPipelineAverage(c, stateRank)) {
                continue;
            }
            List<IssueCoincidenceHistory> h = historiesById.getOrDefault(c.getId(), List.of());
            Duration d = coincidenceWallFirstToLast(h, status, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgCoincidenceLast(
            List<IssueCoincidence> list,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            IssueStatus status,
            LocalDateTime now
    ) {
        int stateRank = pipelineRank(status);
        long sum = 0;
        int n = 0;
        for (IssueCoincidence c : list) {
            if (!includeCoincidenceInPipelineAverage(c, stateRank)) {
                continue;
            }
            List<IssueCoincidenceHistory> h = historiesById.getOrDefault(c.getId(), List.of());
            Duration d = coincidenceLastSegment(h, status, now);
            sum += d.toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgCoincidenceQa(
            List<IssueCoincidence> list,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            LocalDateTime now
    ) {
        int qaRank = pipelineRank(IssueStatus.QA_REJECTED);
        long sum = 0;
        int n = 0;
        for (IssueCoincidence c : list) {
            List<IssueCoincidenceHistory> h = historiesById.getOrDefault(c.getId(), List.of());
            boolean hasQa = h.stream().anyMatch(x -> x.getIssueStatus() == IssueStatus.QA_REJECTED);
            boolean closed = parseCoincidenceStatus(c.getStatus()) == IssueStatus.CLOSED;
            if (!hasQa && !closed) {
                continue;
            }
            if (!includeCoincidenceInPipelineAverage(c, qaRank)) {
                continue;
            }
            sum += coincidenceWallFirstToLast(h, IssueStatus.QA_REJECTED, now).toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration avgCoincidenceOnHold(
            List<IssueCoincidence> list,
            Map<UUID, List<IssueCoincidenceHistory>> historiesById,
            LocalDateTime now
    ) {
        long sum = 0;
        int n = 0;
        for (IssueCoincidence c : list) {
            List<IssueCoincidenceHistory> h = historiesById.getOrDefault(c.getId(), List.of());
            boolean hasHold = h.stream().anyMatch(x -> x.getIssueStatus() == IssueStatus.ON_HOLD);
            boolean closed = parseCoincidenceStatus(c.getStatus()) == IssueStatus.CLOSED;
            if (!hasHold && !closed) {
                continue;
            }
            sum += coincidenceSumSegments(h, IssueStatus.ON_HOLD, now).toNanos();
            n++;
        }
        return n == 0 ? Duration.ZERO : Duration.ofNanos(sum / n);
    }

    private static Duration coincidenceWallFirstToLast(List<IssueCoincidenceHistory> sorted, IssueStatus status, LocalDateTime now) {
        List<IssueCoincidenceHistory> segs = sorted.stream().filter(x -> x.getIssueStatus() == status).toList();
        if (segs.isEmpty()) {
            return Duration.ZERO;
        }
        LocalDateTime start = segs.stream().map(IssueCoincidenceHistory::getStateStartTime).min(Comparator.naturalOrder()).orElseThrow();
        LocalDateTime end = segs.stream().map(h -> endOrNow(h, now)).max(Comparator.naturalOrder()).orElseThrow();
        return Duration.between(start, end);
    }

    private static Duration coincidenceLastSegment(List<IssueCoincidenceHistory> sorted, IssueStatus status, LocalDateTime now) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .max(Comparator.comparing(IssueCoincidenceHistory::getStateStartTime))
                .map(h -> Duration.between(h.getStateStartTime(), endOrNow(h, now)))
                .orElse(Duration.ZERO);
    }

    private static Duration coincidenceSumSegments(List<IssueCoincidenceHistory> sorted, IssueStatus status, LocalDateTime now) {
        return sorted.stream()
                .filter(x -> x.getIssueStatus() == status)
                .map(h -> Duration.between(h.getStateStartTime(), endOrNow(h, now)))
                .reduce(Duration.ZERO, Duration::plus);
    }

    public static MemberStats computeMemberStats(
            UUID memberId,
            List<IssueReport> companyReports,
            Map<UUID, List<IssueReportHistory>> reportHistoriesByReportId,
            List<IssueCoincidence> companyCoincidences,
            Map<UUID, List<IssueCoincidenceHistory>> coincidenceHistoriesById,
            LocalDate weekStartDate,
            LocalDateTime now
    ) {
        List<IssueReport> memberReports = companyReports.stream()
                .filter(r -> r.getAssigneeId() != null && memberId.equals(r.getAssigneeId().userId()))
                .collect(Collectors.toList());

        IssueReportCompanyStats issues = computeIssueReportCompanyStats(
                memberReports, reportHistoriesByReportId, weekStartDate, now);

        List<IssueCoincidence> memberCoincidences = companyCoincidences.stream()
                .filter(c -> c.getIssueReports().stream().anyMatch(
                        ir -> ir.getAssigneeId() != null && memberId.equals(ir.getAssigneeId().userId())))
                .collect(Collectors.toList());

        CoincidenceCompanyStats coin = computeCoincidenceCompanyStats(
                memberCoincidences, coincidenceHistoriesById, weekStartDate, now);

        return new MemberStats(issues.totals(), issues.durations(), coin.totals(), coin.durations());
    }
}
