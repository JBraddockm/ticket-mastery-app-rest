package org.example.service;

import java.util.List;
import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.model.Project;

public interface ProjectService {
  Optional<ProjectDTO> findByProjectCode(String projectCode);

  void deleteByProjectCode(String projectCode);

  ProjectDTO save(ProjectDTO projectDTO);

  void update(ProjectDTO projectDTO);

  void complete(ProjectDTO projectDTO);

  List<ProjectDTO> findAll();

  List<ProjectDTO> findAllByProjectManagerIs(UserDTO manager);

  List<ProjectDTO> findAllByProjectStatusIsNot(Status status);

//  List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);
}
