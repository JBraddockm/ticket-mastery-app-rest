package org.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractMapperService<User, UserDTO> implements UserService {

  private final UserRepository userRepository;

  // TODO Reintroduce PasswordEncoder after implementing Spring Security.
  //  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
    super(modelMapper, User.class, UserDTO.class);
    this.userRepository = userRepository;
  }

  @Override
  public UserDTO save(UserDTO userDTO) {
    User user = this.mapToEntity(userDTO);
    //    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setPassword(userDTO.getPassword());
    user.setEnabled(true);
    userRepository.save(user);
    return this.mapToDTO(user);
  }

  @Override
  public List<UserDTO> findAll() {
    return userRepository.findAll().stream().map(this::mapToDTO).toList();
  }

  @Override
  public Optional<UserDTO> findByUsername(String userName) {
    return userRepository.findByUsername(userName).map(this::mapToDTO);
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
        .map(this::mapToDTO)
        .toList();
  }

  @Override
  public List<UserDTO> findAllEmployees() {
    return userRepository.findAll().stream()
        .filter(user -> user.getRole().getId().equals(3L))
        .map(this::mapToDTO)
        .toList();
  }
}
