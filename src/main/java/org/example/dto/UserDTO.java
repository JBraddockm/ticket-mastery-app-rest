package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Gender;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phoneNumber;
    private RoleDTO roleDTO;
    private Gender gender;
    private boolean isEnabled;

}
