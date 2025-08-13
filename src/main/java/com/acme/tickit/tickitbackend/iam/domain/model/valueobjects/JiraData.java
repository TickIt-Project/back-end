package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.JiraEmailNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.JiraPasswordNotAcceptedException;
import jakarta.persistence.Embeddable;

@Embeddable
public record JiraData(String jiraEmail, String jiraPassword) {
    public JiraData {
        if (jiraEmail != null && !jiraEmail.matches(".+@.+\\..+")) {
            throw new JiraEmailNotAcceptedException();
        }
        if (jiraEmail != null && jiraPassword == null) {
            throw new JiraPasswordNotAcceptedException();
        }
    }
}
