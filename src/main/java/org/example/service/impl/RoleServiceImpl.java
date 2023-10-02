package org.example.service.impl;

import org.example.dto.RoleDTO;
import org.example.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends AbstractMapService<RoleDTO, Long> implements RoleService{
    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        return super.map.put(roleDTO.getId(), roleDTO);
    }

    @Override
    public void saveAll(Map<Long, RoleDTO> roles) {
        super.map.putAll(roles);
    }

    @Override
    public List<RoleDTO> findAll() {
        return super.findAll();
    }

    @Override
    public RoleDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void update(RoleDTO roleDTO) {
        super.update(roleDTO.getId(), roleDTO);
    }
}
