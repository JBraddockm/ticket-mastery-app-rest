package org.example.service;

import org.example.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // TODO Create a findAllByRole method to replace the following two.
    List<UserDTO> findAllManagers();
    List<UserDTO> findAllEmployees();
    List<UserDTO> findAll();
    Optional<UserDTO> findByUsername(String username);
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO dto);
    void deleteByUsername(String userName);
}
