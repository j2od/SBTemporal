package com.innodata.platform.automation.orchestrator.workflow;

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

    @Override
    public WorkflowExecutionResult execute(WorkflowDefinition definition, WorkflowExecutionRequest request) {
        Map<String, Object> results = new HashMap<>();

        for (WorkflowStep step : definition.steps()) {
            ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(step.startToCloseSeconds()))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(step.maximumAttempts()).build())
                    .build();

            ActivityStub activityStub = Workflow.newUntypedActivityStub(options);
            Map<String, Object> activityInput = new HashMap<>(step.input());
            activityInput.put("workflowPayload", request.payload());
            Object result = activityStub.execute(step.activity(), Object.class, activityInput);
            results.put(step.id(), result);
        }

        return new WorkflowExecutionResult(definition.id(), results);
    }
}
