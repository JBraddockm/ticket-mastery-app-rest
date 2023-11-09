package org.example.mapper;

import org.example.dto.RoleDTO;
import org.example.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO roleDTO){
        return modelMapper.map(roleDTO, Role.class);
    }

    public RoleDTO convertToDTO(Role entity){
        return modelMapper.map(entity, RoleDTO.class);
    }
}
