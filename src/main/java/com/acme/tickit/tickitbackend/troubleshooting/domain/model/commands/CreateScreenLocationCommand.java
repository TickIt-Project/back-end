package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.ScreenLocationNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.ScreenUrlNotAcceptedException;

import java.util.UUID;

public record CreateScreenLocationCommand(
        UUID companyId,
        String name,
        String url
) {
    public CreateScreenLocationCommand {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (name == null || name.isEmpty()) {
            throw new ScreenLocationNameNotAcceptedException();
        }
        if (url == null || url.isEmpty()) {
            throw new ScreenUrlNotAcceptedException();
        }
    }
}
