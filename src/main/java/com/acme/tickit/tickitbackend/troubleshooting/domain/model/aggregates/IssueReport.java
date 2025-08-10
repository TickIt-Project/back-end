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
    private CompanyID companyID;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 3500)
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
    @AttributeOverride(name = "userId", column = @Column(name = "reporter_id"))
    private UserID reporterID;

    @Embedded
    @AttributeOverride(name = "userId", column = @Column(name = "assignee_id"))
    private UserID assigneeID;

    private LocalDateTime resolved_at;
    private Boolean ticket_option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_coincidence_id", nullable = true)
    private IssueCoincidence issueCoincidence;


    public IssueReport() {}

    public IssueReport(UUID companyID, String title, String description,
                       ScreenLocation screenLocation, CompanyRole companyRole,
                       Integer severity, String imgUrl, Integer status,
                       UUID reporterID, UUID assigneeID, LocalDateTime resolved_at,
                       Boolean ticket_option, IssueCoincidence issueCoincidence) {
        this.companyID = new CompanyID(companyID);
        this.title = title;
        this.description = description;
        this.screenLocation = screenLocation;
        this.companyRole = companyRole;
        this.severity = Severity.values()[severity];
        this.imgUrl = imgUrl;
        this.status = Status.values()[status];
        this.reporterID = new UserID(reporterID);
        this.assigneeID = new UserID(assigneeID);
        this.resolved_at = resolved_at;
        this.ticket_option = ticket_option;
        this.issueCoincidence = issueCoincidence;
    }
}
