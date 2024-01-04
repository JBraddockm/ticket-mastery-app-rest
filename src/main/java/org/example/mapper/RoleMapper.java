package org.example.mapper;

import org.example.dto.RoleDTO;
import org.example.model.Role;
import org.example.service.impl.AbstractMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractMapperService<Role, RoleDTO> {
    public RoleMapper(ModelMapper modelMapper) {
        super(modelMapper, Role.class, RoleDTO.class);
    }

    @Override
    public Role mapToEntity(RoleDTO type) {
        return super.mapToEntity(type);
    }

    @Override
    public RoleDTO mapToDTO(Role type) {
        return super.mapToDTO(type);
    }
}
