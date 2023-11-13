package org.example.service.impl;

import org.example.dto.RoleDTO;
import org.example.exception.RoleNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.repository.RoleRepository;
import org.example.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return roleRepository.findAll().stream()
                .map(roleMapper::convertToDTO)
                .toList();
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDTO(roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.valueOf(id)))
        );
    }
}
