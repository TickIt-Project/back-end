package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
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
public class CompanyRole extends AuditableModel {

    private String name;

    @Embedded
    private CompanyID companyID;

    @OneToMany(mappedBy = "companyRole", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<IssueReport> issueReports = new ArrayList<>();

    public CompanyRole() {}

    public CompanyRole(String name, UUID companyID) {
        this.name = name;
        this.companyID = new CompanyID(companyID);
    }

    public void addIssueReport(IssueReport report) {
        issueReports.add(report);
        report.setCompanyRole(this);
    }

    public void removeIssueReport(IssueReport report) {
        issueReports.remove(report);
        report.setCompanyRole(null);
    }
}
