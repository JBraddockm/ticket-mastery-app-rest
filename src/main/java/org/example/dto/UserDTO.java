package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.annotation.FieldsValueMatch;
import org.example.annotation.ValidPassword;
import org.example.enums.Gender;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Password does not match"
        )
})
public class UserDTO {

    private Long id;

    @NotBlank(
            message = "First name cannot be empty"
    )
    @Size(
            min = 3, message = "First name must be minimum three characters"
    )
    private String firstName;

    @NotBlank(
            message = "Last name cannot be empty"
    )
    @Size(
            min = 3, message = "Last name must be minimum three characters"
    )
    private String lastName;

    @NotBlank(
            message = "Email cannot be empty"
    )
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Enter a valid email address"
    )
    private String username;

    @ValidPassword
    private String password;

    @NotBlank(
            message = "Confirm the password"
    )
    private String confirmPassword;

    @NotBlank(
            message = "Phone number cannot be empty"
    )
    @Pattern(
            regexp = "^((\\+44)|(0)) ?\\d{4} ?\\d{6}$",
            message = "Enter a valid phone number"
    )
    private String phoneNumber;

    @NotNull(
            message = "Choose a role"
    )
    private RoleDTO role;

    @NotNull(
            message = "Choose a gender"
    )
    private Gender gender;

    private boolean isEnabled;

}
