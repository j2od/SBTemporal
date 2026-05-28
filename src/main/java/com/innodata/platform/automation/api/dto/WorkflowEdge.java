package com.innodata.platform.automation.api.dto;

public record WorkflowEdge (
        String id,
        String source,
        String target
) {}