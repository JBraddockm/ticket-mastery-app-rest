package org.example.converter;

import jakarta.annotation.Nonnull;
import org.example.dto.RoleDTO;
import org.example.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDTOConverter implements Converter<String, RoleDTO> {
    private final RoleService roleService;

    public RoleDTOConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(@Nonnull String source) {
        return roleService.findById(Long.parseLong(source));
    }
}

