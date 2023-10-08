package org.example.converter;

import jakarta.annotation.Nonnull;
import org.example.dto.ProjectDTO;
import org.example.service.ProjectService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProjectDTOConverter implements Converter<String, ProjectDTO> {
    private final ProjectService projectService;

    public ProjectDTOConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectDTO convert(@Nonnull String projectCode) {
        return projectService.findById(projectCode);
    }
}
