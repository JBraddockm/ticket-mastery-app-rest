package org.example.converter;

import jakarta.annotation.Nonnull;
import org.example.dto.RoleDTO;
import org.example.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDTOConverter implements Converter<String, RoleDTO> {
    private final RoleService roleService;

    public RoleDTOConverter(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(@Nonnull String source) {
        if (source.isEmpty()) {
            return null;
        }
        return roleService.findById(Long.valueOf(source));
    }
}

