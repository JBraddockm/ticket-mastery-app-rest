package org.example.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

  @Mock private ModelMapper modelMapper;

  @InjectMocks private UserMapper userMapper;

  @Test
  void shouldMapUserDTOToUser() {
    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");

    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");

    when(modelMapper.map(userDTO, User.class)).thenReturn(user);

    User mappedUser = userMapper.mapToEntity(userDTO);

    verify(modelMapper).map(userDTO, User.class);

    assertEquals(userDTO.getFirstName(), mappedUser.getFirstName());
    assertEquals(userDTO.getLastName(), mappedUser.getLastName());
  }

  @Test
  void shouldMapUserToUserDTO() {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");

    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");

    when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

    UserDTO mappedUserDTO = userMapper.mapToDTO(user);

    verify(modelMapper).map(user, UserDTO.class);

    assertEquals(user.getFirstName(), mappedUserDTO.getFirstName());
    assertEquals(user.getLastName(), mappedUserDTO.getLastName());
  }
}
