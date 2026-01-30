package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.IssueReportNotSavedException;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.Language;
import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotFoundException;
import com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl.ExternalCompanyService;
import com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl.ExternalUserService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.*;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateIssueReportCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportAssigneeCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportStatusCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.events.IssueReportCreatedForCoincidenceEvent;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.ConnectionWords;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueReportCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.IssueReportRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.ScreenLocationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IssueReportCommandServiceImpl implements IssueReportCommandService {

    private static final int MIN_MEANINGFUL_WORDS_FOR_COINCIDENCE = 50;

    private final IssueReportRepository issueReportRepository;
    private final ExternalUserService externalUserService;
    private final ScreenLocationRepository screenLocationRepository;
    private final ExternalCompanyService externalCompanyService;
    private final ApplicationEventPublisher eventPublisher;

    public IssueReportCommandServiceImpl(IssueReportRepository issueReportRepository,
                                         ExternalUserService externalUserService,
                                         ScreenLocationRepository screenLocationRepository,
                                         ExternalCompanyService externalCompanyService,
                                         ApplicationEventPublisher eventPublisher) {
        this.issueReportRepository = issueReportRepository;
        this.externalUserService = externalUserService;
        this.screenLocationRepository = screenLocationRepository;
        this.externalCompanyService = externalCompanyService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UUID handle(CreateIssueReportCommand command) {
        if (!externalCompanyService.ExistsCompanyById(command.companyId()))
            throw new CompanyIdNotFoundException(command.companyId().toString());
        if (!externalUserService.ExistsUserById(command.reporterId()))
            throw new ReporterUserNotFoundException(command.reporterId().toString());
        if (command.screenLocationId() != null && !screenLocationRepository.existsById(command.screenLocationId()))
            throw new ScreenLocationNotFoundException(command.screenLocationId().toString());
        var companyRole = externalUserService.GetCompanyRoleByUserId(command.reporterId()).orElse(null);
        var screenLocation = command.screenLocationId() != null
                ? screenLocationRepository.findById(command.screenLocationId()).orElse(null)
                : null;
        boolean coincidenceAvailable = calculateCoincidenceAvailable(command);
        var issueReport = new IssueReport(command, screenLocation, companyRole, coincidenceAvailable);
        try {
            issueReportRepository.save(issueReport);
            if (coincidenceAvailable) {
                eventPublisher.publishEvent(new IssueReportCreatedForCoincidenceEvent(issueReport.getId()));
            }
        } catch (Exception e) {
            throw new IssueReportNotCreatedException(e.getMessage());
        }
        return issueReport.getId();
    }

    @Override
    public Optional<IssueReport> handle(UpdateIssueReportStatusCommand command) {
        IssueReport issueReport = issueReportRepository.findById(command.issueReportId())
                .orElseThrow(() -> new IssueReportNotFoundException(command.issueReportId().toString()));
        try {
            issueReport.updateStatus(command.status());
            issueReportRepository.save(issueReport);
        } catch (Exception e) {
            throw new IssueReportNotSavedException(e.getMessage());
        }
        return Optional.of(issueReport);
    }

    @Override
    public Optional<IssueReport> handle(UpdateIssueReportAssigneeCommand command) {
        IssueReport issueReport = issueReportRepository.findById(command.issueReportId())
                .orElseThrow(() -> new IssueReportNotFoundException(command.issueReportId().toString()));
        if (!externalUserService.ExistsUserById(command.assigneeId()))
            throw new AssigneeUserNotFoundException(command.assigneeId().toString());
        try {
            issueReport.updateAssigneeId(command.assigneeId());
            issueReportRepository.save(issueReport);
        } catch (Exception e) {
            throw new IssueReportNotSavedException(e.getMessage());
        }
        return Optional.of(issueReport);
    }

    private boolean calculateCoincidenceAvailable(CreateIssueReportCommand command) {
        Language language = externalUserService.getLanguageByUserId(command.reporterId()).orElse(Language.EN);
        String[] meaningfulWords = ConnectionWords.filterMeaningfulWords(command.description(), language);
        boolean hasEnoughWords = meaningfulWords.length >= MIN_MEANINGFUL_WORDS_FOR_COINCIDENCE;
        boolean hasUrl = hasUrl(command.imgUrl()) || hasUrl(command.issueScreenUrl());
        return hasEnoughWords && hasUrl;
    }

    private boolean hasUrl(String url) {
        return url != null && !url.isBlank();
    }
}
