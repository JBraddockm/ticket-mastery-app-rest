package org.example.mapper;

import org.example.dto.TaskDTO;
import org.example.model.Task;
import org.example.service.impl.AbstractMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper extends AbstractMapperService<Task, TaskDTO> {

    public TaskMapper(ModelMapper modelMapper) {
        super(modelMapper, Task.class, TaskDTO.class);
    }

    @Override
    public Task mapToEntity(TaskDTO type) {
        return super.mapToEntity(type);
    }

    @Override
    public TaskDTO mapToDTO(Task type) {
        return super.mapToDTO(type);
    }
}
