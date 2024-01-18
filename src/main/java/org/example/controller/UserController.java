package org.example.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.example.dto.UserDTO;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
    name = "user",
    description = "Everything about User",
    externalDocs =
        @ExternalDocumentation(
            url = "https://github.com/jbraddockm",
            description = "Find out more"))
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @Operation(
      summary = "Get a list of users",
      description = "Returns a list of users",
      operationId = "readAllUsers")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "User", implementation = UserDTO.class, title = "User")))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserDTO> readAllUsers() {
    return userService.findAll();
  }

  @GetMapping("{username}")
  @Operation(
      summary = "Find user by username",
      description = "Returns a single user",
      operationId = "getUserByUsername")
  @Parameters(
      @Parameter(
          name = "username",
          description = "Username of user to return",
          example = "info@example.org"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "User", implementation = UserDTO.class, title = "User"))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content())
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDTO getUserByUsername(@PathVariable("username") String username) {
    return userService
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  @PostMapping
  @Operation(
      summary = "Create a user",
      description = "Create a single user",
      operationId = "createUser")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "User", implementation = UserDTO.class, title = "User"))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
    return userService.create(userDTO);
  }

  @PutMapping
  @Operation(
      summary = "Update an existing user",
      description = "Updates an existing user",
      operationId = "updateUser")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema =
                        @Schema(name = "User", implementation = UserDTO.class, title = "User"))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content()),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content())
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserDTO updateUser(@Valid @RequestBody UserDTO userDTO) {
    return userService.update(userDTO);
  }

  // TODO PatchMapping for partially updating User.

  @DeleteMapping("{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "Delete a user",
      description = "Deletes a single user",
      operationId = "deleteUser")
  @Parameters(
      @Parameter(
          name = "username",
          description = "Username of user to delete",
          example = "info@example.org"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Successful operation",
            content = @Content()),
        @ApiResponse(responseCode = "404", description = "Invalid user value", content = @Content())
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  public void deleteUser(@PathVariable("username") String username) {
    // TODO Check if user can be deleted.
    userService.deleteByUsername(username);
  }
}
