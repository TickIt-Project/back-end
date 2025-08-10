package com.acme.tickit.tickitbackend.iam.domain.model.aggregates;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyCodeNotGeneratedException;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateCompanyCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.JiraData;
import com.acme.tickit.tickitbackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
                   Boolean isJiraActive, Boolean isSlackActive, String code) {
        this.companyName = companyName;
        this.jiraData = new JiraData(jiraEmail, jiraPassword);
        this.isJiraActive = isJiraActive;
        this.isSlackActive = isSlackActive;
        this.code = new CompanyCode(generateCompanyCode(code));
    }

    public Company(CreateCompanyCommand command) {
        this.companyName = command.companyName();
        this.jiraData = new JiraData(command.jiraEmail(), command.jiraPassword());
        this.isJiraActive = command.isJiraActive();
        this.isSlackActive = command.isSlackActive();
        this.code = new CompanyCode(generateCompanyCode(command.companyName()));
    }

    public String generateCompanyCode(String name) {
        int number = ThreadLocalRandom.current().nextInt(101, 999);
        if (name != null && !name.isEmpty()) {
            return "T" + name.toUpperCase().substring(0, 3) + number;
        } else throw new CompanyCodeNotGeneratedException();
    }
}
