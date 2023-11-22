package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.TaskDTO;
import org.example.enums.Status;
import org.example.exception.TaskNotFoundException;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends AbstractCommonService<Task, TaskDTO> implements TaskService {

  private final TaskRepository taskRepository;

  public TaskServiceImpl(
          ModelMapper modelMapper, TaskRepository taskRepository) {
    super(modelMapper, Task.class, TaskDTO.class);
    this.taskRepository = taskRepository;
  }

  @Override
  public TaskDTO save(TaskDTO taskDTO) {
    Task task = this.mapToEntity(taskDTO);
    taskRepository.save(task);
    return this.mapToDTO(task);
  }

  @Override
  public List<TaskDTO> findAll() {
    return taskRepository.findAll().stream().map(this::mapToDTO).toList();
  }

  @Override
  public void update(TaskDTO taskDTO) {

    Task task =
        taskRepository
            .findById(taskDTO.getId())
            .orElseThrow(() -> new TaskNotFoundException(taskDTO.getId().toString()));

    Task updatedTask = this.mapToEntity(taskDTO);
    updatedTask.setId(task.getId());
    updatedTask.setStatus(task.getStatus());

    taskRepository.save(updatedTask);
  }

  @Override
  public void updateStatus(TaskDTO taskDTO) {

    Task task =
        taskRepository
            .findById(taskDTO.getId())
            .orElseThrow(() -> new TaskNotFoundException(taskDTO.getId().toString()));

    task.setStatus(taskDTO.getStatus());

    taskRepository.save(task);
  }

  @Override
  public void deleteById(Long id) {
    taskRepository.deleteById(id);
  }

  @Override
  public Optional<TaskDTO> findById(Long id) {
    return taskRepository.findById(id).map(this::mapToDTO);
  }

  @Override
  public List<TaskDTO> findAllTasksByStatus(Status status) {
    return taskRepository.findTasksByStatusIs(status).stream().map(this::mapToDTO).toList();
  }

  @Override
  public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
    return taskRepository.findTasksByStatusIsNot(status).stream().map(this::mapToDTO).toList();
  }

}
