package com.innodata.platform.automation.api.controller;

import com.innodata.platform.automation.common.constants.TemporalConstants;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowExecutionRequest;
import com.innodata.platform.automation.orchestrator.workflow.WorkflowOrchestrator;
import com.innodata.platform.automation.orchestrator.dsl.WorkflowDefinition;
import com.innodata.platform.automation.api.request.StartWorkflowRequest;
import com.innodata.platform.automation.service.WorkflowDefinitionCompiler;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowClient workflowClient;

    @Autowired
    private WorkflowDefinitionCompiler compilerService;

    @PostMapping("/start")
    public ResponseEntity<String> startWorkflow(@RequestBody StartWorkflowRequest request) {

        WorkflowDefinition definition = compilerService.compile(request, request.workflowId(), request.name());

        WorkflowOrchestrator workflow = workflowClient.newWorkflowStub(WorkflowOrchestrator.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue(TemporalConstants.WORKFLOW_ORCHESTRATION_QUEUE)
                        .setWorkflowId(request.workflowId()).build()
        );

        WorkflowExecutionRequest executionRequest = new WorkflowExecutionRequest(request.payload());
        WorkflowExecution execution = WorkflowClient.start(workflow::execute, definition, executionRequest);
        return ResponseEntity.ok("START!! " + execution.getWorkflowId());
    }
}
