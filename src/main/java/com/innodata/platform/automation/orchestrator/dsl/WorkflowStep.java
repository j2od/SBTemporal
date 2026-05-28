package com.innodata.platform.automation.orchestrator.dsl;

import java.util.List;
import java.util.Map;

public record WorkflowStep(
        String id,
        String activity,
        Map<String, Object> input,
        List<String> dependsOn,
        int startToCloseSeconds,
        int maximumAttempts
) {}
