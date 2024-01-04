package org.example.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.example.dto.TaskDTO;
import org.example.dto.TaskProcessDTO;
import org.example.enums.Status;
import org.example.exception.TaskNotFoundException;
import org.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(
    name = "task",
    description = "Everything about Task",
    externalDocs =
        @ExternalDocumentation(
            url = "https://github.com/jbraddockm",
            description = "Find out more"))
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  @Operation(
      summary = "Get a list of tasks",
      description = "Returns a list of tasks",
      operationId = "readAllTasks")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "Task", implementation = TaskDTO.class, title = "Task")))
      })
  public List<TaskDTO> readAllTasks() {
    return taskService.findAll();
  }

  @GetMapping("{taskId}")
  @Operation(
      summary = "Find task by ID",
      description = "Returns a single task",
      operationId = "getTaskById")
  @Parameters(@Parameter(name = "taskId", description = "ID of task to return", example = "1"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "Task", implementation = TaskDTO.class, title = "Task"))),
        @ApiResponse(responseCode = "404", description = "Task not found", content = @Content())
      })
  public TaskDTO getTaskById(@PathVariable("taskId") Long taskId) {
    return taskService
        .findById(taskId)
        .orElseThrow(() -> new TaskNotFoundException(String.valueOf(taskId)));
  }

  @PostMapping
  @Operation(
      summary = "Create a task",
      description = "Create a single task",
      operationId = "createTask")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(
                            name = "Task",
                            implementation = TaskProcessDTO.class,
                            title = "Task"))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  public TaskDTO createTask(@Valid @RequestBody TaskProcessDTO taskProcessDTO) {
    return taskService.createTask(taskProcessDTO);
  }

  @PutMapping("{taskId}")
  @Operation(
      summary = "Update an existing task",
      description = "Update an existing task",
      operationId = "updateTask")
  @Parameters(@Parameter(name = "taskId", description = "ID of task to update", example = "1"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(
                            name = "Task",
                            implementation = TaskProcessDTO.class,
                            title = "Task"))),
        @ApiResponse(responseCode = "404", description = "Task not found", content = @Content()),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  public TaskDTO updateTask(
      @Valid @RequestBody TaskProcessDTO taskProcessDTO, @PathVariable("taskId") Long taskId) {
    return taskService.updateTask(taskId, taskProcessDTO);
  }

  @DeleteMapping("{taskId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete a task",
      description = "Deletes a single task",
      operationId = "deleteTask")
  @Parameters(@Parameter(name = "taskId", description = "ID of task to delete", example = "1"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Successful operation",
            content = @Content()),
        @ApiResponse(responseCode = "404", description = "Invalid task value", content = @Content())
      })
  public void deleteTask(@PathVariable("taskId") Long taskId) {
    taskService.deleteById(taskId);
  }

  @GetMapping("/employee/pending-tasks")
  @Operation(
      summary = "Get a list of pending tasks",
      description = "Returns a list of an employee's pending tasks",
      operationId = "employeePendingTasks")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "Task", implementation = TaskDTO.class, title = "Task")))
      })
  public List<TaskDTO> employeePendingTasks() {
    return taskService.findAllTasksByStatusIsNot(Status.COMPLETED);
  }

  @PutMapping("/employee/{taskId}/status")
  @Operation(
      summary = "Update status of an existing task",
      description = "Updates status of an existing task",
      operationId = "updateTaskStatus")
  @Parameters(
      @Parameter(name = "taskId", description = "ID of task to update the status", example = "1"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "Task", type = "Task", implementation = TaskDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid status value provided",
            content = @Content())
      })
  public TaskDTO updateTaskStatus(
      @PathVariable("taskId") Long taskId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Map with key-value pairs representing status details",
              content =
                  @Content(
                      examples = @ExampleObject(value = "status: Open"),
                      schema =
                          @Schema(
                              title = "Status",
                              name = "Status",
                              type = "string",
                              allowableValues = "Open, Completed, In Progress")))
          @RequestBody
          Map<String, String> status) {
    return taskService.updateTaskStatus(taskId, Status.fromText(status.get("status")));
  }

  @GetMapping("/employee/archive")
  @Operation(
      summary = "Get the list of completed tasks",
      description = "Returns the list of completed tasks of an employee",
      operationId = "employeeArchivedTasks")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "Task", implementation = TaskDTO.class, title = "Task")))
      })
  public List<TaskDTO> employeeArchivedTasks() {
    return taskService.findAllTasksByStatus(Status.COMPLETED);
  }
}
