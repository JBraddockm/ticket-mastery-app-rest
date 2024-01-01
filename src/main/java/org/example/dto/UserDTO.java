package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
      message = "Password does not match")
})
@Schema(name="User")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

  @Schema(example = "1")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotBlank(message = "First name cannot be empty")
  @Size(min = 3, message = "First name must be minimum three characters")
  @Schema(example = "James")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty")
  @Size(min = 3, message = "Last name must be minimum three characters")
  @Schema(example = "Brown")
  private String lastName;

  @NotBlank(message = "Email cannot be empty")
  @Email(
      regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
      flags = Pattern.Flag.CASE_INSENSITIVE,
      message = "Enter a valid email address")
  @Schema(example = "info@example.org")
  private String username;

  @ValidPassword
  @Schema(example = "AKS_454ab3?")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @NotBlank(message = "Confirm the password")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Schema(example = "AKS_454ab3?")
  private String confirmPassword;

  @NotBlank(message = "Phone number cannot be empty")
  @Pattern(regexp = "^((\\+44)|(0)) ?\\d{4} ?\\d{6}$", message = "Enter a valid phone number")
  @Schema(example = "+447514234568")
  private String phoneNumber;

  @NotNull(message = "Choose a role")
  private RoleDTO role;

  @NotNull(message = "Choose a gender")
  private Gender gender;

  private boolean isEnabled;
}
