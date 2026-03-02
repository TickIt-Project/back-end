package com.acme.tickit.tickitbackend.management.domain.model.queries;

import com.acme.tickit.tickitbackend.management.domain.exceptions.ItMemberStatisticsIdNotAcceptedException;

import java.util.UUID;

public record GetItMemberStatisticsByIdQuery(UUID id) {
    public GetItMemberStatisticsByIdQuery {
        if (id == null) {
            throw new ItMemberStatisticsIdNotAcceptedException();
        }
    }
}
