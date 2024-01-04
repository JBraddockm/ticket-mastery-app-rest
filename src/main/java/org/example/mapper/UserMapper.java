package org.example.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.service.impl.AbstractMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapperService<User, UserDTO> {
  public UserMapper(ModelMapper modelMapper) {
    super(modelMapper, User.class, UserDTO.class);
  }

  @Override
  public User mapToEntity(UserDTO type) {
    return super.mapToEntity(type);
  }

  @Override
  public UserDTO mapToDTO(User type) {
    return super.mapToDTO(type);
  }
}
