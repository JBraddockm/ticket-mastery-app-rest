package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void shouldReturnAllUsers() {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");

    User user2 = new User();
    user2.setFirstName("Jane");
    user2.setLastName("Doe");

    List<User> users = List.of(user, user2);

    when(userRepository.findAll()).thenReturn(users);
    when(userMapper.mapToDTO(any(User.class))).thenReturn(new UserDTO());

    List<UserDTO> userDTOs = userService.findAll();
    List<UserDTO> expectedUserDTOs = users.stream().map(userMapper::mapToDTO).toList();

    verify(userRepository, times(1)).findAll();
    verify(userMapper, times(users.size() * 2)).mapToDTO(any(User.class));
    assertIterableEquals(expectedUserDTOs, userDTOs);
  }

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 3L, 4L})
  void shouldReturnUserById(Long id) {

    User user = new User();
    user.setId(id);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(userMapper.mapToDTO(any(User.class))).thenReturn(new UserDTO());

    Optional<UserDTO> userDTO = userService.findById(id);

    verify(userRepository, times(1)).findById(id);
    verify(userMapper, times(1)).mapToDTO(any(User.class));
    assertTrue(userDTO.isPresent());
  }

  @Test
  void shouldReturnEmptyOptionalWhenUserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    Optional<UserDTO> user = userService.findById(-1L);
    assertTrue(user.isEmpty());
  }
}
