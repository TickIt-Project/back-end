package com.acme.tickit.tickitbackend.troubleshooting.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.Language;
import com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl.ExternalUserService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportCreatedForCoincidenceEvent;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.connectionwords.ConnectionWords;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Keyword;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueCoincidenceRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueReportRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class IssueReportCreatedForCoincidenceEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueReportCreatedForCoincidenceEventHandler.class);
    private static final int MIN_COMMON_WORDS = 5;
    private static final int MIN_MATCHING_CONDITIONS = 3;

    private final IssueReportRepository issueReportRepository;
    private final IssueCoincidenceRepository issueCoincidenceRepository;
    private final ExternalUserService externalUserService;

    public IssueReportCreatedForCoincidenceEventHandler(IssueReportRepository issueReportRepository,
                                                         IssueCoincidenceRepository issueCoincidenceRepository,
                                                         ExternalUserService externalUserService) {
        this.issueReportRepository = issueReportRepository;
        this.issueCoincidenceRepository = issueCoincidenceRepository;
        this.externalUserService = externalUserService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBefore(IssueReportCreatedForCoincidenceEvent event) {
        LOGGER.info("EVENT BEFORE COMMIT {}", event.issueReportId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(IssueReportCreatedForCoincidenceEvent event) {
        Optional<IssueReport> newReportOpt = issueReportRepository.findById(event.issueReportId());
        if (newReportOpt.isEmpty()) {
            LOGGER.warn("IssueReport {} not found for coincidence detection", event.issueReportId());
            return;
        }
        IssueReport newReport = newReportOpt.get();
        if (!Boolean.TRUE.equals(newReport.getCoincidenceAvailable())) {
            return;
        }

        // Same company and same language (EN or ES) for comparison
        Language language = newReport.getLanguage() != null
                ? newReport.getLanguage()
                : externalUserService.getLanguageByUserId(newReport.getReporterId().userId()).orElse(Language.EN);

        var otherReports = issueReportRepository.findByCoincidenceAvailableTrueAndCompanyId_CompanyIdAndLanguageAndIdNot(
                newReport.getCompanyId().companyId(),
                language,
                newReport.getId()
        );

        for (IssueReport otherReport : otherReports) {
            if (meetsCoincidenceCriteria(newReport, otherReport, language)) {
                createIssueCoincidence(newReport, otherReport, language);
            }
        }
    }

    private boolean meetsCoincidenceCriteria(IssueReport report1, IssueReport report2, Language language) {
        int matchingConditions = 0;

        // 1. At least 5 common meaningful words in description
        String[] commonWords = ConnectionWords.findCommonMeaningfulWords(
                report1.getDescription(),
                report2.getDescription(),
                language
        );
        if (commonWords.length >= MIN_COMMON_WORDS) {
            matchingConditions++;
        }

        // 2. Same company role
        if (Objects.equals(
                report1.getCompanyRole() != null ? report1.getCompanyRole().getId() : null,
                report2.getCompanyRole() != null ? report2.getCompanyRole().getId() : null)) {
            matchingConditions++;
        }

        // 3. URLs are the same (imgUrl and issueScreenUrl)
        if (urlsMatch(report1.getImageUrl(), report1.getIssueScreenUrl(),
                report2.getImageUrl(), report2.getIssueScreenUrl())) {
            matchingConditions++;
        }

        // 4. Same screen location
        if (Objects.equals(
                report1.getScreenLocation() != null ? report1.getScreenLocation().getId() : null,
                report2.getScreenLocation() != null ? report2.getScreenLocation().getId() : null)) {
            matchingConditions++;
        }

        return matchingConditions >= MIN_MATCHING_CONDITIONS;
    }

    private boolean urlsMatch(String imgUrl1, String issueScreenUrl1, String imgUrl2, String issueScreenUrl2) {
        String safe1Img = imgUrl1 != null ? imgUrl1.trim() : "";
        String safe1Screen = issueScreenUrl1 != null ? issueScreenUrl1.trim() : "";
        String safe2Img = imgUrl2 != null ? imgUrl2.trim() : "";
        String safe2Screen = issueScreenUrl2 != null ? issueScreenUrl2.trim() : "";
        return safe1Img.equals(safe2Img) && safe1Screen.equals(safe2Screen);
    }

    private void createIssueCoincidence(IssueReport report1, IssueReport report2, Language language) {
        String[] commonWords = ConnectionWords.findCommonMeaningfulWords(
                report1.getDescription(),
                report2.getDescription(),
                language
        );

        String title = report1.getTitle();
        String description = "Coincidence detected: " + String.join(", ", commonWords);

        var coincidence = new IssueCoincidence(
                report1.getCompanyId().companyId(),
                title,
                description,
                language
        );

        Arrays.stream(commonWords)
                .map(Keyword::new)
                .forEach(coincidence::addKeyword);

        coincidence.addIssueReport(report1);
        coincidence.addIssueReport(report2);

        if (report1.getScreenLocation() != null) {
            coincidence.addScreenLocation(report1.getScreenLocation());
        }
        if (report2.getScreenLocation() != null
                && !coincidence.getScreenLocations().contains(report2.getScreenLocation())) {
            coincidence.addScreenLocation(report2.getScreenLocation());
        }

        issueCoincidenceRepository.save(coincidence);
        LOGGER.info("Created IssueCoincidence {} linking reports {} and {}",
                coincidence.getId(), report1.getId(), report2.getId());
    }
}
