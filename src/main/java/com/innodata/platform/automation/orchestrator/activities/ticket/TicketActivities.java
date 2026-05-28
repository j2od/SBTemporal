package com.innodata.platform.automation.orchestrator.activities.ticket;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface TicketActivities {

    @ActivityMethod(name = "createTicket")
    void createTicket(Map<String, Object> input);
}
