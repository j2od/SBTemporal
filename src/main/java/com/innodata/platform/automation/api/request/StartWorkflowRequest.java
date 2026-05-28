package com.innodata.platform.automation.api.request;

import com.innodata.platform.automation.api.dto.WorkflowEdge;
import com.innodata.platform.automation.api.dto.WorkflowNode;

import java.util.List;
import java.util.Map;

public record StartWorkflowRequest(
        // Could also create a separate DTO for the actual flow
        String workflowId,
        String name,
        List<WorkflowNode> nodes,
        List<WorkflowEdge> edges,
        Map<String, Object> payload,
        Object viewport
) {}
