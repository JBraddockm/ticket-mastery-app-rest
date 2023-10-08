package org.example.service;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;

import java.util.List;

public interface ProjectService extends CrudService<ProjectDTO, String>{
    void complete(ProjectDTO projectDTO);
    List<ProjectDTO> findAllNonCompletedProjects();
    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);
}
