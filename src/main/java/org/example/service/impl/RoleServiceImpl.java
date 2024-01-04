package org.example.service.impl;

import java.util.List;
import org.example.dto.RoleDTO;
import org.example.exception.RoleNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }

  @Override
  public List<RoleDTO> findAll() {
    return roleRepository.findAll().stream().map(roleMapper::mapToDTO).toList();
  }

  @Override
  public RoleDTO findById(Long id) {
    return roleMapper.mapToDTO(
        roleRepository
            .findById(id)
            .orElseThrow(() -> new RoleNotFoundException(String.valueOf(id))));
  }
}
