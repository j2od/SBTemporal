package com.innodata.platform.automation.orchestrator.activities.email;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import com.innodata.platform.automation.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component("emailActivitiesImpl")
public class EmailActivitiesImpl implements EmailActivities {

    @Autowired
    private EmailService emailService;

    @Override
    public void sendEmail(ActivityNode activityNode) {
        System.out.println("SEND EMAIL START AT: " + Instant.now());
        Map<String, Object> parameters = (Map<String, Object>) activityNode.getParameters();
        String emailAddress = (String) parameters.get("emailAddress");
        String emailSubject = (String) parameters.get("emailSubject");
        String emailBody = (String) parameters.get("emailBody");
        emailService.sendEmail("dmaybaruc@gmail.com", emailAddress, emailSubject, emailBody);
        System.out.println("SEND EMAIL END AT: " + Instant.now());
    }
}
