package com.acme.tickit.tickitbackend.management.domain.model.entities;

import com.acme.tickit.tickitbackend.management.domain.model.valueobjects.IssueReportId;
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
public class IssueReportHistory extends AuditableModel {

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    private Duration stateDuration;

    private LocalDateTime stateStartTime;
    private LocalDateTime stateEndTime;

    @Embedded
    private IssueReportId issueReportId;

    @Nullable
    private String comment;

    public IssueReportHistory() {}

    public IssueReportHistory(String issueStatus, LocalDateTime stateStartTime, UUID issueReportId, @Nullable String comment) {
        this.issueStatus = IssueStatus.valueOf(issueStatus);
        this.stateStartTime = stateStartTime;
        this.issueReportId = new IssueReportId(issueReportId);
        this.comment = comment;
    }

    public void closeState(LocalDateTime stateEndTime) {
        this.stateEndTime = stateEndTime;
        this.stateDuration = Duration.between(stateStartTime, stateEndTime);
    }
}
