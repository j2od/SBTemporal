package com.innodata.platform.automation.service;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import com.innodata.platform.automation.api.dto.nodes.ApprovalNode;
import com.innodata.platform.automation.api.dto.nodes.EndNode;
import com.innodata.platform.automation.api.dto.nodes.StartNode;
import com.innodata.platform.automation.common.enums.Activities;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowStep;
import com.innodata.platform.automation.api.request.StartWorkflowRequest;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowDefinition;
import com.innodata.platform.automation.api.dto.WorkflowNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowDefinitionCompiler {

    public WorkflowDefinition compile(StartWorkflowRequest request, String workflowId, String workflowName) {
        HashMap<String, WorkflowNode> nodeMap = validateNodes(request);
        List<WorkflowStep> steps = toWorkflowStepDefinition(nodeMap, request);
        return new WorkflowDefinition(workflowId, workflowName, steps);
    }

    private List<WorkflowStep> toWorkflowStepDefinition(HashMap<String, WorkflowNode> nodeMap, StartWorkflowRequest request) {
        List<WorkflowStep> steps = new ArrayList<>();
        for (WorkflowNode node : request.nodes()) {
            WorkflowNode validatedNode = nodeMap.get(node.getId());
            if (validatedNode == null) {
                throw new IllegalStateException("Node not found in validated map: " + node.getId());
            }
            steps.add(new WorkflowStep(validatedNode.getId(), validatedNode.getType(), validatedNode));
        }
        return steps;
    }

    private HashMap<String, WorkflowNode> validateNodes(StartWorkflowRequest request) {
        HashMap<String, WorkflowNode> nodeMap = new HashMap<>();

        for (WorkflowNode node : request.nodes()) {
            if (nodeMap.containsKey(node.getId())) {
                throw new IllegalArgumentException("Duplicate node ID: " + node.getId());
            }
            nodeMap.put(node.getId(), node);

            switch (node) {
                case StartNode start -> {
                    if (start.getLabel() == null || start.getLabel().isBlank()) {
                        throw new IllegalArgumentException("START node must have a label: " + node.getId());
                    }
                }
                case EndNode end -> {
                    if (end.getLabel() == null || end.getLabel().isBlank()) {
                        throw new IllegalArgumentException("END node must have a label: " + node.getId());
                    }
                }
                case ActivityNode activity -> {
                    if (activity.getActivity() == null || activity.getActivity().isBlank()) {
                        throw new IllegalArgumentException("ACTIVITY node missing 'activity' field: " + node.getId());
                    }
                    if (activity.getParameters() == null || activity.getParameters().isEmpty()) {
                        throw new IllegalArgumentException("ACTIVITY node missing 'input' parameters: " + node.getId());
                    }
                    if (!Activities.isAllowed(activity.getActivity())) {
                        throw new IllegalArgumentException("Invalid activity type: " + activity.getActivity());
                    }
                }
                case ApprovalNode approval -> {
                    if (approval.getRole() == null || approval.getRole().isBlank()) {
                        throw new IllegalArgumentException("APPROVAL node must specify a role: " + node.getId());
                    }
                    if (approval.getMessage() == null || approval.getMessage().isBlank()) {
                        throw new IllegalArgumentException("APPROVAL node must specify a message: " + node.getId());
                    }
                }
                default -> throw new IllegalArgumentException("Unsupported node type: " + node.getType());
            }
        }

        return nodeMap;
    }
}
