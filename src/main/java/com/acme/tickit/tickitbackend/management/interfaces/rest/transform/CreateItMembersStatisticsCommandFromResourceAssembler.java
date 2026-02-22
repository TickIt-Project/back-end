package com.acme.tickit.tickitbackend.management.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.management.domain.model.commands.CreateOrUpdateItMemberStatisticsCommand;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.CreateItMemberStatisticsResource;

public class CreateItMembersStatisticsCommandFromResourceAssembler {
    public static CreateOrUpdateItMemberStatisticsCommand toCommandFromResource(CreateItMemberStatisticsResource resource) {
        return new CreateOrUpdateItMemberStatisticsCommand(
                resource.userId()
        );
    }
}
