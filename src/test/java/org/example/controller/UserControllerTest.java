package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ExtendWith(SpringExtension.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldReturnUnAuthorizedClientError() throws Exception {
    mockMvc
        .perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(
      username = "admin",
      authorities = {"ADMIN"})
  void shouldReturnAllUsers() throws Exception {
    mockMvc
        .perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].firstName").exists());
  }

  @ParameterizedTest
  @WithMockUser(
      username = "admin",
      authorities = {"ADMIN"})
  @ValueSource(strings = {"dummyusername"})
  void shouldReturnUserNotFoundErrorMessage(String username) throws Exception {
    mockMvc
        .perform(get("/api/v1/users/{username}", username).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.error").value("User dummyusername could not be found"));
  }

  @ParameterizedTest
  @WithMockUser(
      username = "admin",
      authorities = {"ADMIN"})
  @ValueSource(strings = {"dummyusername"})
  void shouldReturnUserNotFoundException(String username) throws Exception {
    MvcResult result =
        mockMvc
            .perform(get("/api/v1/users/{username}", username).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    Exception resolvedException = result.getResolvedException();
    assertNotNull(resolvedException);
    assertEquals(UserNotFoundException.class, resolvedException.getClass());
  }

  @ParameterizedTest
  @WithMockUser(
      username = "admin",
      authorities = {"ADMIN"})
  @ValueSource(strings = {"johnkelly"})
  void shouldReturnUserDTO(String username) throws Exception {
    mockMvc
        .perform(get("/api/v1/users/{username}", username).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void createUser() {}

  @Test
  void updateUser() {}

  @Test
  void deleteUser() {}
}
