package org.example.service;

import java.util.List;
import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.enums.Status;

public interface ProjectService {
  ProjectDTO save(ProjectDTO projectDTO);

  List<ProjectDTO> findAll();

  Optional<ProjectDTO> findByProjectCode(String projectCode);

  ProjectDTO createProject(ProjectDTO projectDTO);

  void deleteByProjectCode(String projectCode);

  ProjectDTO updateProject(String projectCode, ProjectDTO projectDTO);

  ProjectDTO completeProject(String projectCode);

  List<ProjectDTO> findAllByProjectManagerIs();

  List<ProjectDTO> findAllByProjectStatusIsNot(Status status);
}
