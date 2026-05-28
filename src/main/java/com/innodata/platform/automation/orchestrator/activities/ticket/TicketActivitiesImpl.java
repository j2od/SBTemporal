package com.innodata.platform.automation.orchestrator.activities.ticket;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component("ticketActivitiesImpl")
public class TicketActivitiesImpl implements TicketActivities {

    @Override
    public void createTicket(Map<String, Object> activityData) {
        System.out.println("CREATE TICKET START AT: " + Instant.now());
        System.out.println("CREATE TICKET END AT: " + Instant.now());
    }
}
