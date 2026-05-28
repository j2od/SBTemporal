package com.innodata.platform.automation.service;

import com.innodata.platform.automation.api.dto.WorkflowEdge;
import com.innodata.platform.automation.common.enums.Activities;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowStep;
import com.innodata.platform.automation.api.request.StartWorkflowRequest;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowDefinition;
import com.innodata.platform.automation.api.dto.WorkflowNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowDefinitionCompiler {

    public WorkflowDefinition compile(StartWorkflowRequest request, String workflowId, String workflowName) {
        Map<String, WorkflowNode> nodeMap = request.nodes()
                .stream()
                .collect(Collectors.toMap(WorkflowNode::id, node -> node));

        List<WorkflowStep> steps = request.nodes()
                .stream()
                .filter(node -> "activity".equals(node.type()))
                .map(node -> toWorkflowStepDefinition(request, nodeMap, node))
                .toList();

        List<WorkflowStep> sortedSteps = topologicalSort(steps);
        return new WorkflowDefinition(workflowId, workflowName, sortedSteps);
    }

    @SuppressWarnings("unchecked")
    private WorkflowStep toWorkflowStepDefinition(StartWorkflowRequest request, Map<String, WorkflowNode> nodeMap, WorkflowNode node) {
        Map<String, Object> data = node.data() == null ? Map.of() : node.data();
        String nodeId = node.id();

        String activity = (String) data.get("activity");
        if (!Activities.isAllowed(activity)) {
            throw new IllegalArgumentException("Activity not permitted: " + activity);
        }

        Map<String, Object> input = (Map<String, Object>) data.getOrDefault("input", Map.of());
        List<String> dependsOn = request.edges()
                .stream()
                .filter(edge -> edge.target().equals(nodeId))
                .map(WorkflowEdge::source)
                .filter(sourceId -> "activity".equals(nodeMap.get(sourceId).type()))
                .toList();

        Map<String, Object> retry = (Map<String, Object>) data.getOrDefault("retry", Map.of());
        int maximumAttempts = ((Number) retry.getOrDefault("maximumAttempts", 3)).intValue();
        int timeout = ((Number) data.getOrDefault("startToCloseSeconds", 60)).intValue();
        return new WorkflowStep(nodeId, activity, input, dependsOn, timeout, maximumAttempts);
    }

    private List<WorkflowStep> topologicalSort(List<WorkflowStep> steps) {
        List<WorkflowStep> sortedSteps = new ArrayList<>();

        Map<String, WorkflowStep> pending = steps
                .stream()
                .collect(Collectors.toMap(WorkflowStep::id, step -> step));

        while (!pending.isEmpty()) {
            List<WorkflowStep> ready = pending.values()
                    .stream()
                    .filter(step -> sortedSteps.stream()
                            .map(WorkflowStep::id)
                            .collect(Collectors.toSet())
                            .containsAll(step.dependsOn()))
                    .toList();

            if (ready.isEmpty()) {
                throw new IllegalArgumentException("Workflow contains cycle.");
            }

            ready.forEach(step -> {
                sortedSteps.add(step);
                pending.remove(step.id());
            });
        }

        return sortedSteps;
    }
}
