package org.example.service;

import org.example.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO, String> {
    List<UserDTO> findAllManagers();
    List<UserDTO> findAllEmployees();
}
