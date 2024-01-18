package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.KeycloakUserService;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final RoleService roleService;

  private final KeycloakUserService keycloakUserService;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserMapper userMapper,
      RoleService roleService,
      KeycloakUserService keycloakUserService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.roleService = roleService;
    this.keycloakUserService = keycloakUserService;
  }

  @Override
  public UserDTO save(UserDTO userDTO) {
    User user = userMapper.mapToEntity(userDTO);
    userRepository.save(user);
    return userMapper.mapToDTO(user);
  }

  @Override
  public UserDTO create(UserDTO userDTO) {
    // TODO Handle User creation better: Currently, it creates User in Keycloak first before
    // password gets encoded for UserDTO
    keycloakUserService.createUser(userDTO);
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userDTO.setEnabled(true);
    // TODO Retrieve role from userDTO and pass it to Keycloak
    roleService.assignRole(
        keycloakUserService.getUserByUsername(userDTO.getUsername()).getId(), "training-admin");
    return this.save(userDTO);
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
    User user =
        userRepository
            .findByUsername(userDTO.getUsername())
            .orElseThrow(() -> new UserNotFoundException(userDTO.getUsername()));

    userDTO.setId(user.getId());

    // TODO Change update methods and only require certain properties in the payload. Remove
    // password.
    userDTO.setPassword(passwordEncoder.encode(user.getPassword()));

    userDTO.setEnabled(true);

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
