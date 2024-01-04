package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.dto.TaskDTO;
import org.example.dto.TaskProcessDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.TaskNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.mapper.TaskMapper;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final ProjectService projectService;
  private final UserService userService;
  private final TaskMapper taskMapper;

  public TaskServiceImpl(
      TaskRepository taskRepository,
      ProjectService projectService,
      UserService userService,
      TaskMapper taskMapper) {
    this.taskRepository = taskRepository;
    this.projectService = projectService;
    this.userService = userService;
    this.taskMapper = taskMapper;
  }

  @Override
  public TaskDTO save(TaskDTO taskDTO) {
    Task task = taskMapper.mapToEntity(taskDTO);
    taskRepository.save(task);
    return taskMapper.mapToDTO(task);
  }

  @Override
  public TaskDTO processTask(TaskProcessDTO taskProcessDTO) {

    ProjectDTO projectDTO =
        projectService
            .findByProjectCode(taskProcessDTO.getProjectCode())
            .orElseThrow(() -> new ProjectNotFoundException(taskProcessDTO.getProjectCode()));

    UserDTO userDTO =
        userService
            .findByUsername(taskProcessDTO.getAssignedEmployee())
            .orElseThrow(() -> new UserNotFoundException(taskProcessDTO.getAssignedEmployee()));

    TaskDTO taskDTO = new TaskDTO();

    taskDTO.setProject(projectDTO);
    taskDTO.setAssignedEmployee(userDTO);
    taskDTO.setTaskDetail(taskProcessDTO.getTaskDetail());
    taskDTO.setTaskSubject(taskProcessDTO.getTaskSubject());
    taskDTO.setAssignedDate(taskProcessDTO.getAssignedDate());

    if (taskProcessDTO.getId() != null) {
      this.findById(taskProcessDTO.getId()).ifPresent(task -> taskDTO.setStatus(task.getStatus()));
    } else {
      taskDTO.setStatus(Status.OPEN);
    }

    taskDTO.setId(taskProcessDTO.getId());

    return this.save(taskDTO);
  }

  @Override
  public List<TaskDTO> findAll() {
    return taskRepository.findAll().stream().map(taskMapper::mapToDTO).toList();
  }

  @Override
  public TaskDTO updateTask(Long taskId, TaskProcessDTO taskProcessDTO) {

    this.findById(taskId)
        .ifPresentOrElse(
            task -> taskProcessDTO.setId(taskId),
            () -> {
              throw new TaskNotFoundException(String.valueOf(taskId));
            });

    return this.processTask(taskProcessDTO);
  }

  @Override
  public TaskDTO createTask(TaskProcessDTO taskProcessDTO) {
    return this.processTask(taskProcessDTO);
  }

  @Override
  public TaskDTO updateTaskStatus(Long taskId, Status status) {

    TaskDTO taskDTO = this.findById(taskId).orElseThrow(() -> new TaskNotFoundException(String.valueOf(taskId)));

    taskDTO.setStatus(status);

    return this.save(taskDTO);
  }

  @Override
  public void deleteById(Long taskId) {
    // TODO Implement soft delete
    this.findById(taskId)
        .ifPresentOrElse(
            task -> taskRepository.deleteById(taskId),
            () -> {
              throw new TaskNotFoundException(String.valueOf(taskId));
            });
  }

  @Override
  public Optional<TaskDTO> findById(Long id) {
    return taskRepository.findById(id).map(taskMapper::mapToDTO);
  }

  @Override
  public List<TaskDTO> findAllTasksByStatus(Status status) {
    // TODO Only retrieve tasks by an logged in Employee.
    return taskRepository.findTasksByStatusIs(status).stream().map(taskMapper::mapToDTO).toList();
  }

  @Override
  public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
    // TODO Only retrieve tasks by an logged in Employee.
    return taskRepository.findTasksByStatusIsNot(status).stream()
        .map(taskMapper::mapToDTO)
        .toList();
  }
}
