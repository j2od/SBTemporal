package com.innodata.platform.automation.orchestrator.activities.content;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ContentActivities {

    @ActivityMethod(name = "contentAcquisition")
    String extractMetadata(String filePath);
}
