package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateScreenLocationCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ScreenLocation extends AuditableModel {

    private String name;
    private String url;

    @Embedded
    private CompanyID companyId;

    @OneToMany(mappedBy = "screenLocation", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<IssueReport> issueReports = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "screen_location_issue_coincidence",
            joinColumns = @JoinColumn(name = "screen_location_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_coincidence_id")
    )
    private List<IssueCoincidence> issueCoincidences = new ArrayList<>();

    public ScreenLocation() {}

    public ScreenLocation(String name, String url, UUID companyID) {
        this.name = name;
        this.url = url;
        this.companyId = new CompanyID(companyID);
    }

    public ScreenLocation(CreateScreenLocationCommand command) {
        this.name = command.name();
        this.url = command.url();
        this.companyId = new CompanyID(command.companyId());
    }

    public void addIssueReport(IssueReport report) {
        issueReports.add(report);
        report.setScreenLocation(this);
    }

    public void removeIssueReport(IssueReport report) {
        issueReports.remove(report);
        report.setScreenLocation(null);
    }

    /**
     * add and remove issue coincidence
     */
}
