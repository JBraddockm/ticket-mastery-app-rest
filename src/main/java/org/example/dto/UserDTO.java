package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Gender;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    @NotBlank(message = "First name cannot be empty.")
    @Size(min = 3, message = "First name must be minimum three characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    @Size(min = 3, message = "Last name must be minimum three characters.")
    private String lastName;

    @NotBlank
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Enter a valid email address."
    )
    private String userName;
//
//    @NotBlank
//    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^((\\+44)|(0)) ?\\d{4} ?\\d{6}$",
            message = "Enter a valid phone number."
    )
    private String phoneNumber;

    @NotNull
    private RoleDTO roleDTO;

    @NotNull
    private Gender gender;

    private boolean isEnabled;

}
