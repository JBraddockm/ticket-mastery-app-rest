package org.example.service.impl;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends AbstractMapService<UserDTO, String> implements UserService {
    @Override
    public UserDTO save(UserDTO userDTO) {
        return super.save(userDTO.getUserName(), userDTO);
    }

    @Override
    public void saveAll(Map<String, UserDTO> users) {
        super.saveAll(users);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public UserDTO findById(String userName) {
        return super.findById(userName);
    }

    @Override
    public void deleteById(String userName) {
        super.deleteById(userName);
    }

    @Override
    public void update(UserDTO userDTO) {
        super.update(userDTO.getUserName(), userDTO);
    }

    @Override
    public List<UserDTO> findAllManagers() {
        return super.findAll().stream()
                .filter(userDTO -> userDTO.getRoleDTO().getId() == 2L)
                .toList();
    }

    @Override
    public List<UserDTO> findAllEmployees() {
        return super.findAll().stream()
                .filter(userDTO -> userDTO.getRoleDTO().getId() == 3L)
                .toList();
    }
}
