package org.example.service;

import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;

import java.util.*;

public interface TaskService extends CrudService<TaskDTO, Long>{
    List<TaskDTO> findTasksByManager(UserDTO manager);

    List<TaskDTO> findAllTasksByStatus(Status status);

    List<TaskDTO> findAllTasksByStatusIsNot(Status status);

    Map<Boolean, List<TaskDTO>> partitionTasksByStatusAndByManager(UserDTO manager);

    void updateStatus(TaskDTO task);
}
