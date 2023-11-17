package org.example.service.impl;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl extends AbstractCommonService<Project, ProjectDTO>
    implements ProjectService {

  private final TaskService taskService;
  private final ProjectRepository projectRepository;

  public ProjectServiceImpl(
      TaskService taskService, ProjectRepository projectRepository, ModelMapper modelMapper) {
    super(modelMapper, Project.class, ProjectDTO.class);
    this.taskService = taskService;
    this.projectRepository = projectRepository;
  }

  @Override
  public ProjectDTO save(ProjectDTO projectDTO) {

    projectDTO.setProjectStatus(Status.OPEN);

    Project project = this.mapToEntity(projectDTO);

    projectRepository.save(project);

    return this.mapToDTO(project);
  }

  public List<ProjectDTO> findAll() {
    return projectRepository.findAll().stream().map(this::mapToDTO).toList();
  }

  @Override
  public void update(ProjectDTO projectDTO) {

    Project project =
        projectRepository
            .findByProjectCode(projectDTO.getProjectCode())
            .orElseThrow(() -> new ProjectNotFoundException(projectDTO.getProjectCode()));

    Project updatedProject = this.mapToEntity(projectDTO);

    // TODO Check if ID still needed. Add a hidden field to the form if needed.
    updatedProject.setId(project.getId());

    projectRepository.save(updatedProject);
  }

  @Override
  public void deleteByProjectCode(String projectCode) {
    projectRepository.deleteByProjectCode(projectCode);
  }

  public Optional<ProjectDTO> findByProjectCode(String projectCode) {
    return projectRepository.findByProjectCode(projectCode).map(this::mapToDTO);
  }

  @Override
  public void complete(ProjectDTO projectDTO) {
    // TODO
    projectRepository
        .findByProjectCode(projectDTO.getProjectCode())
        .ifPresent(project -> project.setProjectStatus(Status.COMPLETED));
  }

  @Override
  public List<ProjectDTO> findAllNonCompletedProjects() {
    return projectRepository.findAll().stream()
        .filter(projectDTO -> !projectDTO.getProjectStatus().equals(Status.COMPLETED))
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
    return projectRepository.findAll().stream()
        .map(this::mapToDTO)
        .filter(projectDTO -> projectDTO.getProjectManager().equals(manager))
        .map(
            projectDTO -> {

              // Completed Tasks
              int completeTaskCounts =
                  (int)
                      taskService.partitionTasksByStatusAndByManager(manager).get(true).stream()
                          .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
                          .count();

              // Unfinished Tasks
              int unfinishedTaskCounts =
                  (int)
                      taskService.partitionTasksByStatusAndByManager(manager).get(false).stream()
                          .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
                          .count();

              projectDTO.setCompleteTaskCounts(completeTaskCounts);
              projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);

              return projectDTO;
            })
        .toList();
  }
}
