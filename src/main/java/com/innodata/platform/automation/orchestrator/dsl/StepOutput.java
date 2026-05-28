package com.innodata.platform.automation.orchestrator.dsl;

public record StepOutput(
        String stepId,
        Object value
) {}
