package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.mapper.ProjectMapper;
import org.example.mapper.UserMapper;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final UserMapper userMapper;
  private final UserService userService;

  public ProjectServiceImpl(
      ProjectRepository projectRepository,
      ProjectMapper projectMapper,
      UserMapper userMapper,
      UserService userService) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.userMapper = userMapper;
    this.userService = userService;
  }

  @Override
  public ProjectDTO save(ProjectDTO projectDTO) {

    Project project = projectMapper.mapToEntity(projectDTO);

    projectRepository.save(project);

    return projectMapper.mapToDTO(project);
  }

  public List<ProjectDTO> findAll() {
    return projectRepository.findAll().stream().map(projectMapper::mapToDTO).toList();
  }

  public Optional<ProjectDTO> findByProjectCode(String projectCode) {
    return projectRepository.findByProjectCode(projectCode).map(projectMapper::mapToDTO);
  }

  @Override
  public ProjectDTO createProject(ProjectDTO projectDTO) {
    if (projectDTO.getProjectStatus() == null) {
      projectDTO.setProjectStatus(Status.OPEN);
    }
    return this.save(projectDTO);
  }

  @Override
  public ProjectDTO updateProject(String projectCode, ProjectDTO projectDTO) {

    this.findByProjectCode(projectCode)
        .ifPresentOrElse(
            project -> {
              projectDTO.setProjectCode(project.getProjectCode());
              projectDTO.setId(project.getId());
            },
            () -> {
              throw new ProjectNotFoundException(projectCode);
            });

    return this.save(projectDTO);
  }

  @Override
  public void deleteByProjectCode(String projectCode) {

    // TODO Change Project Code during soft delete.

    this.findByProjectCode(projectCode)
        .ifPresentOrElse(
            projectDTO -> projectRepository.deleteByProjectCode(projectCode),
            () -> {
              throw new ProjectNotFoundException(projectCode);
            });
  }

  @Override
  public ProjectDTO completeProject(String projectCode) {
    ProjectDTO projectDTO =
        this.findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    projectDTO.setProjectStatus(Status.COMPLETED);
    // TODO Handle pending tasks in this completed project.
    return this.save(projectDTO);
  }

  @Override
  public List<ProjectDTO> findAllByProjectStatusIsNot(Status status) {
    return projectRepository.findAllByProjectStatusIsNot(status).stream()
        .map(projectMapper::mapToDTO)
        .toList();
  }

  @Override
  public List<ProjectDTO> findAllByProjectManagerIs() {

    // TODO Get the logged in manager.
    UserDTO manager =
        userService
            .findByUsername("johnkelly@example.com")
            .orElseThrow(() -> new UserNotFoundException("johnkelly@example.com"));

    return projectRepository.findAllByProjectManagerIs(userMapper.mapToEntity(manager)).stream()
        .map(projectMapper::mapToDTO)
        .toList();
  }
}
