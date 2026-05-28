package com.innodata.platform.automation.orchestrator.activities.email;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component("emailActivitiesImpl")
public class EmailActivitiesImpl implements EmailActivities {

    @Override
    public void sendEmail(Map<String, Object> activityData) {
        System.out.println("SEND EMAIL START AT: " + Instant.now());
        System.out.println("SEND EMAIL END AT: " + Instant.now());
    }
}
