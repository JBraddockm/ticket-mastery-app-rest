package org.example.service;

import java.util.*;
import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;

public interface TaskService {

  TaskDTO save(TaskDTO taskDTO);

  void update(TaskDTO taskDTO);

  Optional<TaskDTO> findById(Long id);

  void deleteById(Long id);

  List<TaskDTO> findAll();

  List<TaskDTO> findTasksByManager(UserDTO manager);

  List<TaskDTO> findAllTasksByStatus(Status status);

  List<TaskDTO> findAllTasksByStatusIsNot(Status status);

  Map<Boolean, List<TaskDTO>> partitionTasksByStatusAndByManager(UserDTO manager);

  void updateStatus(TaskDTO task);
}
