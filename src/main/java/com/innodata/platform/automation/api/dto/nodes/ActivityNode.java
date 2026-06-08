package com.innodata.platform.automation.api.dto.nodes;

import com.innodata.platform.automation.api.dto.WorkflowNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ActivityNode extends WorkflowNode {
    private String label;
    private String activity;
    private Map<String, Object> parameters;
    private Integer startToCloseSeconds;
    private Integer retryAttempts;
}
