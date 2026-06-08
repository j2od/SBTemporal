package com.innodata.platform.automation.api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import com.innodata.platform.automation.api.dto.nodes.ApprovalNode;
import com.innodata.platform.automation.api.dto.nodes.EndNode;
import com.innodata.platform.automation.api.dto.nodes.StartNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartNode.class, name = "start"),
        @JsonSubTypes.Type(value = EndNode.class, name = "end"),
        @JsonSubTypes.Type(value = ActivityNode.class, name = "activity"),
        @JsonSubTypes.Type(value = ApprovalNode.class, name = "approval")
})
public abstract class WorkflowNode {
    private String id;
    private String type;
}