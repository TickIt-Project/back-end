package com.acme.tickit.tickitbackend.management.domain.services;

import com.acme.tickit.tickitbackend.management.domain.model.aggregates.ItMemberStatistics;
import com.acme.tickit.tickitbackend.management.domain.model.commands.CreateOrUpdateItMemberStatisticsCommand;

import java.util.Optional;
import java.util.UUID;

public interface ItMemberStatisticsCommandService {

    /**
     * Creates or updates ItMemberStatistics for the given user (IT head or IT member).
     * Manual endpoint: considers issue reports modified from Sunday of current week to now.
     * If one exists for this week (created during the week), it will be updated.
     */
    UUID handle(CreateOrUpdateItMemberStatisticsCommand command);

    /**
     * Runs for all IT heads and IT members at Saturday midnight.
     * Full week: Sunday to Saturday midnight.
     * Creates new stats or updates existing ones (e.g. manual ones created during the week).
     */
    void createOrUpdateForAllItMembers();
}
