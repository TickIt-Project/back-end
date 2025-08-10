package com.acme.tickit.tickitbackend.iam.domain.model.aggregates;

import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.JiraData;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Company extends AuditableAbstractAggregateRoot<Company> {

    private String companyName;

    @Embedded
    private JiraData jiraData;

    private Boolean isJiraActive;
    private Boolean isSlackActive;

    @Embedded
    private CompanyCode code;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Company() {}

    public Company(String companyName, String jiraEmail, String jiraPassword,
                   Boolean isJiraActive,  Boolean isSlackActive, String code) {
        this.companyName = companyName;
        this.jiraData = new JiraData(jiraEmail, jiraPassword);
        this.isJiraActive = isJiraActive;
        this.isSlackActive = isSlackActive;
        this.code = new CompanyCode(code);
    }
}
