package com.acme.tickit.tickitbackend.management.domain.exceptions;

public class ItMemberStatisticsIdNotAcceptedException extends RuntimeException {
    public ItMemberStatisticsIdNotAcceptedException() {
        super("This IT Member Statistics id is not accepted");
    }
}
