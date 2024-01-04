package org.example.mapper;

import org.example.dto.ProjectDTO;
import org.example.model.Project;
import org.example.service.impl.AbstractMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper extends AbstractMapperService<Project, ProjectDTO> {
  public ProjectMapper(ModelMapper modelMapper) {
    super(modelMapper, Project.class, ProjectDTO.class);
  }

  @Override
  public Project mapToEntity(ProjectDTO type) {
    return super.mapToEntity(type);
  }

  @Override
  public ProjectDTO mapToDTO(Project type) {
    return super.mapToDTO(type);
  }
}
