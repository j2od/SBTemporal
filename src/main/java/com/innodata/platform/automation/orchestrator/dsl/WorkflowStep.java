package com.innodata.platform.automation.orchestrator.dsl;

import com.innodata.platform.automation.api.dto.WorkflowNode;

public record WorkflowStep(
        String id,
        String type,
        WorkflowNode workflowNode
) {}
