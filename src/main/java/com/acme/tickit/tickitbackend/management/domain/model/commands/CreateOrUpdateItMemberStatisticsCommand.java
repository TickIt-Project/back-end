package com.acme.tickit.tickitbackend.management.domain.model.commands;

import com.acme.tickit.tickitbackend.management.domain.exceptions.ItMemberStatisticsIdNotAcceptedException;

import java.util.UUID;

public record CreateOrUpdateItMemberStatisticsCommand(
        UUID userId
) {
    public CreateOrUpdateItMemberStatisticsCommand {
        if (userId == null) {
            throw new ItMemberStatisticsIdNotAcceptedException();
        }
    }
}
