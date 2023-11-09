package org.example.service;

import org.example.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAllManagers();
    List<UserDTO> findAllEmployees();
    List<UserDTO> findAll();
    Optional<UserDTO> findByUserName(String username);
    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO dto);
    void deleteByUserName(String userName);



}
