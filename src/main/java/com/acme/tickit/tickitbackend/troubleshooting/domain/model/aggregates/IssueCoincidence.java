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

    @OneToMany(mappedBy = "issueCoincidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueReport> issueReports = new ArrayList<>();

    private Boolean jiraSynced;
    private LocalDateTime jiraSyncedAt;

    @ElementCollection
    @CollectionTable(
            name = "issue_report_keywords",
            joinColumns = @JoinColumn(name = "issue_report_id")
    )
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "issueCoincidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScreenLocation> screenLocations = new ArrayList<>();

    public IssueCoincidence() {}

    public IssueCoincidence(UUID companyID, String title, String description,
                            Boolean jiraSynced, LocalDateTime jiraSyncedAt) {
        this.companyID = new CompanyID(companyID);
        this.title = title;
        this.description = description;
        this.jiraSynced = jiraSynced;
        this.jiraSyncedAt = jiraSyncedAt;
    }
}
