package com.innodata.platform.automation.api.dto;

import java.util.Map;

public record WorkflowNode (
        String id,
        String type,
        Map<String, Object> data
) {}