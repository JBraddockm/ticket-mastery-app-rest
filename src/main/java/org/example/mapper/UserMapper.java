package org.example.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO entity){
        return modelMapper.map(entity, User.class);
    }
}
