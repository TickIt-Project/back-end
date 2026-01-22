package com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates;

import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Severity;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class IssueReport extends AuditableAbstractAggregateRoot<IssueReport> {

    @Embedded
    private CompanyID companyId;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private ScreenLocation screenLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_role_id", nullable = false)
    private CompanyRole companyRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    private String imgUrl;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_coincidence_id")
    private IssueCoincidence issueCoincidence;

    public IssueReport() {}

    public IssueReport(UUID companyId, String title, String description,
                       ScreenLocation screenLocation, CompanyRole companyRole,
                       Integer severity, String imgUrl, UUID reporterId,
                       LocalDateTime resolvedAt) {
        this.companyId = new CompanyID(companyId);
        this.title = title;
        this.description = description;
        this.screenLocation = screenLocation;
        this.companyRole = companyRole;
        this.severity = Severity.values()[severity];
        this.imgUrl = imgUrl;
        this.status = Status.OPEN;
        this.reporterId = new UserID(reporterId);
        this.assigneeId = null;
        this.resolvedAt = resolvedAt;
        this.ticketOption = false;
        this.issueCoincidence = null;
    }
}
