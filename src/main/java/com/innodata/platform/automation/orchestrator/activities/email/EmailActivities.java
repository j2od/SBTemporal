package com.innodata.platform.automation.orchestrator.activities.email;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface EmailActivities {

    @ActivityMethod(name = "sendEmail")
    void sendEmail(ActivityNode activityNode);
}
