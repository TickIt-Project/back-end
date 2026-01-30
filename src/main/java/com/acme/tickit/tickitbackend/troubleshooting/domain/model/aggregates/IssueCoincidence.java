package com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates;

import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Keyword;
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
public class IssueCoincidence extends AuditableAbstractAggregateRoot<IssueCoincidence> {

    @Embedded
    private CompanyID companyID;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 3500)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "issue_coincidence_issue_reports",
            joinColumns = @JoinColumn(name = "issue_coincidence_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_report_id")
    )
    private List<IssueReport> issueReports = new ArrayList<>();

    private Boolean jiraSynced;
    private LocalDateTime jiraSyncedAt;

    @ElementCollection
    @CollectionTable(
            name = "issue_coincidence_keywords",
            joinColumns = @JoinColumn(name = "issue_coincidence_id")
    )
    private List<Keyword> keywords = new ArrayList<>();

    @ManyToMany(mappedBy = "issueCoincidences")
    private List<ScreenLocation> screenLocations = new ArrayList<>();

    public IssueCoincidence() {}

    public IssueCoincidence(UUID companyID, String title, String description) {
        this.companyID = new CompanyID(companyID);
        this.title = title;
        this.description = description;
        this.jiraSynced = false;
        this.jiraSyncedAt = null;
    }

    // --- issueReports (ManyToMany) ---

    public void addIssueReport(IssueReport report) {
        if (!issueReports.contains(report)) {
            issueReports.add(report);
            report.getIssueCoincidences().add(this);
        }
    }

    public void removeIssueReport(IssueReport report) {
        if (issueReports.remove(report)) {
            report.getIssueCoincidences().remove(this);
        }
    }

    // --- keywords (ElementCollection) ---

    public void addKeyword(Keyword keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
    }

    // --- screenLocations (ManyToMany) ---

    public void addScreenLocation(ScreenLocation screenLocation) {
        if (!screenLocations.contains(screenLocation)) {
            screenLocations.add(screenLocation);
            screenLocation.getIssueCoincidences().add(this);
        }
    }

    public void removeScreenLocation(ScreenLocation screenLocation) {
        if (screenLocations.remove(screenLocation)) {
            screenLocation.getIssueCoincidences().remove(this);
        }
    }
}
