package com.innodata.platform.automation.orchestrator.dsl;

import java.util.Map;

public record WorkflowExecutionResult(
        String workflowId,
        Map<String, Object> results
) {}
