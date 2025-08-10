package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.*;

public record CreateCompanyCommand(String companyName, String jiraEmail,
                                   String jiraPassword, Boolean isJiraActive,
                                   Boolean isSlackActive) {
    public CreateCompanyCommand {
        if (companyName == null || companyName.isEmpty()) {
            throw new CompanyNameNotAcceptedException();
        }
        if (jiraEmail != null && !jiraEmail.matches(".+@.+\\..+")) {
            throw new JiraEmailNotAcceptedException();
        }
        if (jiraEmail != null && jiraPassword == null) {
            throw new JiraPasswordNotAcceptedException();
        }
        if (isJiraActive == null) {
            throw new IsJiraActiveNotAcceptedException();
        }
        if (isSlackActive == null) {
            throw new IsSlackActiveNotAcceptedException();
        }
    }
}
