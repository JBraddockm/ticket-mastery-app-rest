package org.example.service.impl;

import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO, Long> implements TaskService {

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        return super.save(taskDTO.getId(), taskDTO);
    }

    @Override
    public void saveAll(Map<Long, TaskDTO> map) {
        super.saveAll(map);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO taskDTO) {
        super.update(taskDTO.getId(), taskDTO);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findTasksByManager(UserDTO manager) {
        return super.findAll().stream()
                .filter(taskDTO -> taskDTO.getProject().getProjectManager().equals(manager))
                .toList();
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {
        return super.findAll().stream()
                .filter(taskDTO -> taskDTO.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
        return super.findAll().stream()
                .filter(taskDTO -> !taskDTO.getStatus().equals(status))
                .toList();
    }

    @Override
    public Map<Boolean, List<TaskDTO>> partitionTasksByStatusAndByManager(UserDTO manager) {
        return this.findTasksByManager(manager).stream()
                .collect(Collectors.partitioningBy(taskDTO -> taskDTO.getStatus().equals(Status.COMPLETED)));
    }

    @Override
    public void updateStatus(TaskDTO task) {
        super.findById(task.getId()).setStatus(task.getStatus());
        this.update(task);
    }
}
