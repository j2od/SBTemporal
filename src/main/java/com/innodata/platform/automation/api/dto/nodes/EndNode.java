package com.innodata.platform.automation.api.dto.nodes;

import com.innodata.platform.automation.api.dto.WorkflowNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndNode extends WorkflowNode {
    private String label;
}
