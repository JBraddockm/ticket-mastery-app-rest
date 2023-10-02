package org.example.service;

import org.example.dto.UserDTO;

import java.util.*;

public interface UserService extends CrudService<UserDTO, String> {
    List<UserDTO> findAllManagers();
}
