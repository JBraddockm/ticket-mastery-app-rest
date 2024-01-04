package org.example.service;

import java.util.*;

import org.example.dto.TaskProcessDTO;
import org.example.dto.TaskDTO;
import org.example.enums.Status;

public interface TaskService {

  Optional<TaskDTO> findById(Long id);

  List<TaskDTO> findAll();

  TaskDTO save(TaskDTO taskDTO);

  TaskDTO updateTask(Long id, TaskProcessDTO taskProcessDTO);
  TaskDTO createTask(TaskProcessDTO taskProcessDTO);

  TaskDTO processTask(TaskProcessDTO taskProcessDTO);

  void deleteById(Long id);

  List<TaskDTO> findAllTasksByStatus(Status status);

  List<TaskDTO> findAllTasksByStatusIsNot(Status status);

  TaskDTO updateTaskStatus(Long taskId, Status status);
}
