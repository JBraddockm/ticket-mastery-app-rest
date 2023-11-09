package org.example.service;

import org.example.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDTO> findAll();

    RoleDTO findById(Long id);
}
