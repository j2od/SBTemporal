package com.innodata.platform.automation.orchestrator.activities.ticket;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface TicketActivities {

    @ActivityMethod(name = "createTicket")
    void createTicket(ActivityNode activityNode);
}
