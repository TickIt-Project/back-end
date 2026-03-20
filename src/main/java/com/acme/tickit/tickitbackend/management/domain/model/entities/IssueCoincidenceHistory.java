package com.acme.tickit.tickitbackend.management.domain.model.entities;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueCoincidenceId;
import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueStatus;
import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class IssueCoincidenceHistory extends AuditableModel {

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    private Duration stateDuration;

    private LocalDateTime stateStartTime;
    private LocalDateTime stateEndTime;

    @Embedded
    private IssueCoincidenceId issueCoincidenceId;

    @Nullable
    private String comment;

    public IssueCoincidenceHistory() {}

    public IssueCoincidenceHistory(String issueStatus, LocalDateTime stateStartTime, UUID issueCoincidenceId, @Nullable String comment) {
        this.issueStatus = IssueStatus.valueOf(issueStatus);
        this.stateStartTime = stateStartTime;
        this.issueCoincidenceId = new IssueCoincidenceId(issueCoincidenceId);
        this.comment = comment;
    }

    public void closeState(LocalDateTime stateEndTime) {
        this.stateEndTime = stateEndTime;
        this.stateDuration = Duration.between(stateStartTime, stateEndTime);
    }
}
