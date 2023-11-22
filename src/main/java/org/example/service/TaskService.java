package org.example.service;

import java.util.*;
import org.example.dto.TaskDTO;
import org.example.enums.Status;
import org.example.model.Task;

public interface TaskService {

  TaskDTO save(TaskDTO taskDTO);

  void update(TaskDTO taskDTO);

  Optional<TaskDTO> findById(Long id);

  void deleteById(Long id);

  List<TaskDTO> findAll();

  List<TaskDTO> findAllTasksByStatus(Status status);

  List<TaskDTO> findAllTasksByStatusIsNot(Status status);

  void updateStatus(TaskDTO task);

  TaskDTO mapToDTO(Task task);

  Task mapToEntity(TaskDTO taskDTO);
}
