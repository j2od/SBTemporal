package com.innodata.platform.automation.orchestrator.workflow;

import com.innodata.platform.automation.orchestrator.dsl.WorkflowDefinition;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowExecutionRequest;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowExecutionResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowOrchestrator {

    @WorkflowMethod
    WorkflowExecutionResult execute(WorkflowDefinition definition, WorkflowExecutionRequest request);
}
