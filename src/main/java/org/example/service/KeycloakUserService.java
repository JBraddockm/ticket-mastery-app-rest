package org.example.service;

import org.example.dto.UserDTO;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    UserDTO createUser(UserDTO userDTO);
    UserRepresentation getUserByUsername(String username);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);

    UserResource getUserResource(String userId);

}
