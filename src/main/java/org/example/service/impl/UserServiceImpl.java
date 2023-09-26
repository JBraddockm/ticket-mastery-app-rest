package org.example.service.impl;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.stereotype.Component;
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
}
