package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.mapper.ProjectMapper;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final TaskService taskService;
  private final UserService userService;

  public ProjectServiceImpl(
      @Lazy TaskService taskService,
      ProjectRepository projectRepository,
      ProjectMapper projectMapper,
      UserService userService) {
    this.taskService = taskService;
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.userService = userService;
  }

  @Override
  public ProjectDTO save(ProjectDTO projectDTO) {

    if (projectDTO.getProjectStatus() == null) {
      projectDTO.setProjectStatus(Status.OPEN);
    }

    Project project = projectMapper.mapToEntity(projectDTO);

    projectRepository.save(project);

    return projectMapper.mapToDTO(project);
  }

  public List<ProjectDTO> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::mapToDTO).toList();
  }

  @Override
  public void update(ProjectDTO projectDTO) {

    Project updatedProject = projectMapper.mapToEntity(projectDTO);

    projectRepository.save(updatedProject);
  }

  @Override
  public void deleteByProjectCode(String projectCode) {
    projectRepository.deleteByProjectCode(projectCode);
  }

  public Optional<ProjectDTO> findByProjectCode(String projectCode) {
    return projectRepository.findByProjectCode(projectCode).map(projectMapper::mapToDTO);
  }

  @Override
  public void complete(ProjectDTO projectDTO) {
    Project project =
        projectRepository
            .findById(projectDTO.getId())
            .orElseThrow(() -> new ProjectNotFoundException(projectDTO.getProjectCode()));

    project.setProjectStatus(Status.COMPLETED);
    projectRepository.save(project);
  }

  @Override
  public List<ProjectDTO> findAllByProjectStatusIsNot(Status status) {
    return projectRepository.findAllByProjectStatusIsNot(status).stream()
        .map(projectMapper::mapToDTO)
        .toList();
  }

  @Override
  public List<ProjectDTO> findAllByProjectManagerIs(UserDTO manager) {
    //    return
    // projectRepository.findAllByProjectManagerIs(userService.mapToEntity(manager)).stream()
    //        .map(this::mapToDTO)
    //        .toList();
    return null;
  }

  //  @Override
  //  public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
  //    return this.findAllByProjectManagerIs(manager).stream()
  //        .map(
  //            projectDTO -> {
  //              Map<Boolean, List<TaskDTO>> collect =
  //                  projectDTO.getTasks().stream()
  //                      .map(taskService::mapToDTO)
  //                      .collect(
  //                          Collectors.partitioningBy(
  //                              taskDTO -> taskDTO.getStatus().equals(Status.COMPLETED)));
  //
  //              // Completed Tasks
  //              int completeTaskCounts = collect.get(true).size();
  //
  //              // Unfinished Tasks
  //              int unfinishedTaskCounts = collect.get(false).size();
  //
  //              projectDTO.setCompleteTaskCounts(completeTaskCounts);
  //              projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);
  //
  //              return projectDTO;
  //            })
  //        .toList();
  //  }
}
