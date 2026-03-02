package com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates;

import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.Language;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateIssueReportCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Severity;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class IssueReport extends AuditableAbstractAggregateRoot<IssueReport> {

    @Embedded
    private CompanyID companyId;

    @Column(name = "title", length = 100)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private ScreenLocation screenLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_role_id")
    private CompanyRole companyRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "reporter_user_id", nullable = false))
    private UserID reporterId;

    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "assignee_user_id"))
    private UserID assigneeId;

    private LocalDateTime resolvedAt;
    private Boolean ticketOption;
    private String issueScreenUrl;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToMany(mappedBy = "issueReports")
    private List<IssueCoincidence> issueCoincidences = new ArrayList<>();

    private Boolean coincidenceAvailable;

    public IssueReport() {}

    public IssueReport(UUID companyId, String title, String description,
                       ScreenLocation screenLocation, CompanyRole companyRole,
                       String severity, String imgUrl, UUID reporterId,
                       String issueScreenUrl, String language) {
        this.companyId = new CompanyID(companyId);
        this.title = title;
        this.description = description;
        this.screenLocation = screenLocation;
        this.companyRole = companyRole;
        this.severity = Severity.valueOf(severity);
        this.imageUrl = imgUrl;
        this.status = Status.OPEN;
        this.reporterId = new UserID(reporterId);
        this.assigneeId = null;
        this.resolvedAt = null;
        this.ticketOption = false;
        this.issueScreenUrl = issueScreenUrl;
        this.coincidenceAvailable = false;
        this.language = Language.valueOf(language);
    }

    public IssueReport(CreateIssueReportCommand command, ScreenLocation screenLocation,
                       CompanyRole companyRole, Boolean coincidenceAvailable, Language language) {
        this.companyId = new CompanyID(command.companyId());
        this.title = command.title();
        this.description = command.description();
        this.screenLocation = screenLocation;
        this.companyRole = companyRole;
        this.severity = Severity.valueOf(command.severity());
        this.imageUrl = command.imgUrl();
        this.status = Status.OPEN;
        this.reporterId = new UserID(command.reporterId());
        this.assigneeId = null;
        this.resolvedAt = null;
        this.ticketOption = false;
        this.issueScreenUrl = command.issueScreenUrl();
        this.coincidenceAvailable = coincidenceAvailable;
        this.language = language;
    }

    public void updateStatus(String newStatus) {
        if (newStatus.toUpperCase().equals(Status.OPEN.name()) ||
            newStatus.toUpperCase().equals(Status.IN_PROGRESS.name()) ||
            newStatus.toUpperCase().equals(Status.ON_HOLD.name()) ||
            newStatus.toUpperCase().equals(Status.CLOSED.name()) ||
            newStatus.toUpperCase().equals(Status.CANCELLED.name())) {
            this.status = Status.valueOf(newStatus);
            if (this.status == Status.CLOSED) {
                this.resolvedAt = LocalDateTime.now();
            }
        }
    }

    public void updateAssigneeId(UUID assigneeId) {
        this.assigneeId = new UserID(assigneeId);
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
