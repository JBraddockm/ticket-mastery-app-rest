package org.example.converter;

import jakarta.annotation.Nonnull;
import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDTOConverter implements Converter<String, UserDTO> {
    private final UserService userService;

    public UserDTOConverter(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO convert(@Nonnull String userName) {
        return userService.findByUserName(userName).orElseThrow();
    }
}
