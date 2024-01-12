package org.example.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.example.dto.ProjectDTO;
import org.example.exception.ProjectNotFoundException;
import org.example.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(
        name = "project",
        description = "Everything about Project",
        externalDocs =
        @ExternalDocumentation(
                url = "https://github.com/jbraddockm",
                description = "Find out more"))
public class ProjectController {
  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  @Operation(
      summary = "Get a list of projects",
      description = "Returns a list of projects",
      operationId = "readAllProjects")
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
                            name = "Project",
                            implementation = ProjectDTO.class,
                            title = "Project")))
      })
  public List<ProjectDTO> readAllProjects() {
    return projectService.findAll();
  }

  @GetMapping("{projectCode}")
  @Operation(
      summary = "Find project by project code",
      description = "Returns a single project",
      operationId = "getProjectById")
  @Parameters(
      @Parameter(
          name = "projectCode",
          description = "Project code of project to return",
          example = "P1001"))
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
                            name = "Project",
                            implementation = ProjectDTO.class,
                            title = "Project"))),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content())
      })
  public ProjectDTO getProjectById(@PathVariable("projectCode") String projectCode) {
    return projectService
        .findByProjectCode(projectCode)
        .orElseThrow(() -> new ProjectNotFoundException(projectCode));
  }

  @PostMapping
  @Operation(
      summary = "Create a project",
      description = "Create a single project",
      operationId = "createProject")
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
                            name = "Project",
                            implementation = ProjectDTO.class,
                            title = "Project"))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  public ProjectDTO createProject(@Valid @RequestBody ProjectDTO projectDTO) {
    return projectService.createProject(projectDTO);
  }

  @PutMapping("{projectCode}")
  @Operation(
      summary = "Update an existing project",
      description = "Update an existing project",
      operationId = "updateProject")
  @Parameters(
      @Parameter(
          name = "projectCode",
          description = "Project code of project to update",
          example = "P1001"))
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
                            name = "Project",
                            implementation = ProjectDTO.class,
                            title = "Project"))),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @Content()),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  public ProjectDTO updateProject(
      @PathVariable("projectCode") String projectCode, @Valid @RequestBody ProjectDTO projectDTO) {
    return projectService.updateProject(projectCode, projectDTO);
  }

  @DeleteMapping("{projectCode}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete a project",
      description = "Deletes a single project",
      operationId = "deleteByProjectCode")
  @Parameters(
      @Parameter(
          name = "projectCode",
          description = "Project code of project to delete",
          example = "P1001"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Successful operation",
            content = @Content()),
        @ApiResponse(
            responseCode = "404",
            description = "Invalid project value",
            content = @Content())
      })
  public void deleteProject(@PathVariable("projectCode") String projectCode) {
    projectService.deleteByProjectCode(projectCode);
  }

  @GetMapping("/manager/project-status")
  @Operation(
          summary = "Get a list of projects by manager",
          description = "Returns a list of projects of a manager",
          operationId = "getProjectsByManager")
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
                                          name = "Project",
                                          implementation = ProjectDTO.class,
                                          title = "Project")))
          })
  public List<ProjectDTO> getProjectsByManager() {
    return projectService.findAllByProjectManagerIs();
  }

  @PutMapping("/manager/{projectCode}/complete")
  @Operation(
          summary = "Complete a project",
          description = "Complete a project",
          operationId = "completeProject")
  @Parameters(
          @Parameter(
                  name = "projectCode",
                  description = "Project code of project to complete",
                  example = "P1001"))
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
                                          name = "Project",
                                          implementation = ProjectDTO.class,
                                          title = "Project"))),
                  @ApiResponse(responseCode = "404", description = "Project not found", content = @Content())
          })
  public ProjectDTO completeProject(@PathVariable("projectCode") String projectCode) {
    return projectService.completeProject(projectCode);
  }
}
