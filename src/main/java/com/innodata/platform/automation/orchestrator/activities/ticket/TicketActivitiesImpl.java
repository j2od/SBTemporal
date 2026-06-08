package com.innodata.platform.automation.orchestrator.activities.ticket;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component("ticketActivitiesImpl")
public class TicketActivitiesImpl implements TicketActivities {

    @Override
    public void createTicket(ActivityNode activityNode) {
        System.out.println("CREATE TICKET START AT: " + Instant.now());
        Map<String, Object> parameters = (Map<String, Object>) activityNode.getParameters();
        String project = (String) parameters.get("project");
        String ticketType = (String) parameters.get("ticketType");
        System.out.println("Project: " + project);
        System.out.println("Ticket Type: " + ticketType);
        System.out.println("CREATE TICKET END AT: " + Instant.now());
    }
}
