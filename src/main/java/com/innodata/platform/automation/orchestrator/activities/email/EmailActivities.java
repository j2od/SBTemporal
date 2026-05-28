package com.innodata.platform.automation.orchestrator.activities.email;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.Map;

@ActivityInterface
public interface EmailActivities {

    @ActivityMethod(name = "sendEmail")
    void sendEmail(Map<String, Object> input);
}
