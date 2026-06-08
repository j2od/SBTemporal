package com.innodata.platform.automation.orchestrator.workflow;

import com.innodata.platform.automation.api.dto.nodes.ActivityNode;
import com.innodata.platform.automation.api.dto.nodes.ApprovalNode;
import com.innodata.platform.automation.common.constants.TemporalConstants;
import com.innodata.platform.automation.orchestrator.dsl.*;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.ActivityStub;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@WorkflowImpl(taskQueues = TemporalConstants.WORKFLOW_ORCHESTRATION_QUEUE)
public class WorkflowOrchestratorImpl implements WorkflowOrchestrator {

    private static final String STR_NODE_START = "START";
    private static final String STR_NODE_END = "END";
    private static final String STR_NODE_ACTIVITY = "ACTIVITY";
    private static final String STR_NODE_APPROVAL = "APPROVAL";

    private final Map<String, Boolean> approvals = new HashMap<>();

    @Override
    public WorkflowExecutionResult execute(WorkflowDefinition definition, WorkflowExecutionRequest request) {
        for (WorkflowStep step : definition.steps()) {
            switch (step.type().toUpperCase()) {
                case STR_NODE_START:
                    System.out.println("Workflow started: " + step.id());
                    break;
                case STR_NODE_ACTIVITY:
                    ActivityNode activity = (ActivityNode) step.workflowNode();
                    executeActivity(activity, request.payload());
                    break;
                case STR_NODE_APPROVAL:
                    ApprovalNode approval = (ApprovalNode) step.workflowNode();
                    waitForApproval(approval);
                    break;
                case STR_NODE_END:
                    System.out.println("Workflow ended: " + step.id());
                    break;
            }
        }
        return new WorkflowExecutionResult(definition.id());
    }

    @Override
    public void approve(String nodeId) {
        approvals.put(nodeId, true);
    }

    @Override
    public void reject(String nodeId) {
        approvals.put(nodeId, false);
    }

    private void executeActivity(ActivityNode activityNode, Map<String, Object> payload) {

        ActivityOptions options = ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(activityNode.getStartToCloseSeconds()))
                .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(activityNode.getRetryAttempts()).build())
                .build();
        ActivityStub activityStub = Workflow.newUntypedActivityStub(options);
        Object result = activityStub.execute(activityNode.getActivity(), Object.class, activityNode);
    }

    private void waitForApproval(ApprovalNode approvalNode) {
        Workflow.await(() -> approvals.containsKey(approvalNode.getId()));
        Boolean decision = approvals.get(approvalNode.getId());
        if (Boolean.TRUE.equals(decision)) {
            System.out.println("Approval granted for node: " + approvalNode.getId());
        } else {
            System.out.println("Approval rejected for node: " + approvalNode.getId());
            throw new RuntimeException("Approval rejected for node: " + approvalNode.getId());
        }
    }
}
