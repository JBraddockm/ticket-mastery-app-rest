package org.example.service;

import java.util.List;
import java.util.Optional;
import org.example.dto.UserDTO;
import org.example.model.User;

public interface UserService {
    // TODO Create a findAllByRole method to replace the following two.
    List<UserDTO> findAllManagers();
    List<UserDTO> findAllEmployees();
    List<UserDTO> findAll();
    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO dto);
    void deleteByUsername(String username);
}
