package org.example.service.impl;

import java.util.List;
import org.example.dto.RoleDTO;
import org.example.exception.RoleNotFoundException;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractCommonService<Role, RoleDTO> implements RoleService {

  private final RoleRepository roleRepository;

  public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
    super(modelMapper, Role.class, RoleDTO.class);
    this.roleRepository = roleRepository;
  }

  @Override
  public List<RoleDTO> findAll() {
    return roleRepository.findAll().stream().map(this::mapToDTO).toList();
  }

  @Override
  public RoleDTO findById(Long id) {
    return this.mapToDTO(
        roleRepository
            .findById(id)
            .orElseThrow(() -> new RoleNotFoundException(String.valueOf(id))));
  }
}
