package com.acme.tickit.tickitbackend.management.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ItMemberStatisticsIdNotAcceptedException extends DomainException {
    public ItMemberStatisticsIdNotAcceptedException() {
        super("This IT Member Statistics id is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
