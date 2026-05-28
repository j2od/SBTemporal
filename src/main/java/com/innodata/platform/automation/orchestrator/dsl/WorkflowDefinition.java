package com.innodata.platform.automation.orchestrator.dsl;

import java.util.List;

public record WorkflowDefinition(
        String id,
        String name,
        List<WorkflowStep> steps
) {}
