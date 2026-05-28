package com.innodata.platform.automation.orchestrator.dsl;

import java.util.Map;

public record WorkflowExecutionRequest(
        Map<String, Object> payload
) {}
