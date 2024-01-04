package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  // TODO Reintroduce PasswordEncoder after implementing Spring Security.
  //  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public UserDTO save(UserDTO userDTO) {
    User user = userMapper.mapToEntity(userDTO);
    //    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setPassword(userDTO.getPassword());
    user.setEnabled(true);
    userRepository.save(user);
    return userMapper.mapToDTO(user);
  }

  @Override
  public List<UserDTO> findAll() {
    return userRepository.findAll().stream().map(userMapper::mapToDTO).toList();
  }

  @Override
  public Optional<UserDTO> findByUsername(String userName) {
    return userRepository.findByUsername(userName).map(userMapper::mapToDTO);
  }

  @Override
  public void deleteByUsername(String username) {
    this.findByUsername(username)
        .ifPresentOrElse(
            user -> userRepository.deleteByUsername(username),
            () -> {
              throw new UserNotFoundException(username);
            });
  }

  @Override
  public UserDTO update(UserDTO userDTO) {

    // Find the user
    User user =
        userRepository
            .findByUsername(userDTO.getUsername())
            .orElseThrow(() -> new UserNotFoundException(userDTO.getUsername()));

    // Get the User ID and Set it to updated user
    userDTO.setId(user.getId());

    // TODO Password should not be in the user update form. A new method and controller is needed.
    userDTO.setPassword(user.getPassword());
    //    updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));

    userDTO.setEnabled(true);

    // Save and return the updated user
    return this.save(userDTO);
  }

  @Override
  public List<UserDTO> findAllManagers() {
    return userRepository.findAll().stream()
        .filter(user -> user.getRole().getId().equals(2L))
        .map(userMapper::mapToDTO)
        .toList();
  }

  @Override
  public List<UserDTO> findAllEmployees() {
    return userRepository.findAll().stream()
        .filter(user -> user.getRole().getId().equals(3L))
        .map(userMapper::mapToDTO)
        .toList();
  }
}
