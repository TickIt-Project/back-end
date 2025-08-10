package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    private CompanyID companyID;

    @OneToMany(mappedBy = "screenLocation", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<IssueReport> issueReports = new ArrayList<>();

    @OneToMany(mappedBy = "screenLocation", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<IssueCoincidence> issueCoincidences = new ArrayList<>();

    public ScreenLocation() {}

    public ScreenLocation(String name, String url, UUID companyID) {
        this.name = name;
        this.url = url;
        this.companyID = new CompanyID(companyID);
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
