package org.example.service.impl;

import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        userRepository.save(user);
        return userMapper.convertToDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDTO)
                .toList();
    }

    @Override
    public Optional<UserDTO> findByUsername(String userName) {
        return userRepository.findByUsername(userName)
                .map(userMapper::convertToDTO);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        // Find the user
        User user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new UserNotFoundException(userDTO.getUsername()));

        // Convert DTO to User
        User updatedUser = userMapper.convertToEntity(userDTO);

        // Get the User ID and Set it to updated user
        updatedUser.setId(user.getId());

        // Save updated User.
        userRepository.save(updatedUser);

        // Return the updated user
        return this.findByUsername(updatedUser.getUsername()).orElseThrow();
    }

    @Override
    public List<UserDTO> findAllManagers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().getId().equals(2L))
                .map(userMapper::convertToDTO)
                .toList();
    }

    @Override
    public List<UserDTO> findAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().getId().equals(3L))
                .map(userMapper::convertToDTO)
                .toList();
    }
}
