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
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
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
    public Optional<UserDTO> findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(userMapper::convertToDTO);
    }

    @Override
    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        // Find the user
        User user = userRepository.findByUserName(userDTO.getUserName()).orElseThrow(() -> new UserNotFoundException(userDTO.getUserName()));

        // Convert DTO to User
        User updatedUser = userMapper.convertToEntity(userDTO);

        // Get the User ID and Set it to updated user
        updatedUser.setId(user.getId());

        // Save updated User.
        userRepository.save(updatedUser);

        // Return the updated user
        return this.findByUserName(updatedUser.getUserName()).orElseThrow();
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
