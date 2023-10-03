package org.example.service;

import org.example.dto.ProjectDTO;

public interface ProjectService extends CrudService<ProjectDTO, String>{
    void complete(ProjectDTO projectDTO);
}
