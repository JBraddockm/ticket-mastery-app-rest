package org.example.service.impl;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import org.example.dto.KeycloakProperties;
import org.example.dto.UserDTO;
import org.example.service.KeycloakUserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {

  private final Keycloak keycloak;
  private final KeycloakProperties keycloakProperties;

  public KeycloakUserServiceImpl(Keycloak keycloak, KeycloakProperties keycloakProperties) {
    this.keycloak = keycloak;
    this.keycloakProperties = keycloakProperties;
  }

  @Override
  public UserDTO createUser(UserDTO userDTO) {

    UserRepresentation keycloakUser = getUserRepresentation(userDTO);

    UsersResource usersResource = getUsersResource();

    Response response = usersResource.create(keycloakUser);

    if (Objects.equals(201, response.getStatus())) {

      List<UserRepresentation> representationList =
          usersResource.searchByUsername(userDTO.getUsername(), true);
      if (!CollectionUtils.isEmpty(representationList)) {
        UserRepresentation userRepresentation1 =
            representationList.stream()
                // Change to false and enable emailVerification() if verification is implemented..
                .filter(userRepresentation -> Objects.equals(true, keycloakUser.isEmailVerified()))
                .findFirst()
                .orElse(null);
        assert userRepresentation1 != null;
        //        emailVerification(userRepresentation1.getId());
      }

      return userDTO;
    }

    return null;
  }

  private UserRepresentation getUserRepresentation(UserDTO userDTO) {
    UserRepresentation keycloakUser = new UserRepresentation();

    keycloakUser.setFirstName(userDTO.getFirstName());
    keycloakUser.setLastName(userDTO.getLastName());
    keycloakUser.setUsername(userDTO.getUsername());
    keycloakUser.setEmail(userDTO.getEmail());
    keycloakUser.setEmailVerified(true);
    keycloakUser.setEnabled(true);

    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

    credentialRepresentation.setValue(userDTO.getPassword());
    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
    credentialRepresentation.setTemporary(false);

    List<CredentialRepresentation> list = List.of(credentialRepresentation);

    keycloakUser.setCredentials(list);
    return keycloakUser;
  }

  @Override
  public UserRepresentation getUserByUsername(String username) {
    // TODO Add an exception for Keycloak.
    return getUsersResource().searchByUsername(username, true).get(0);
  }

  private UsersResource getUsersResource() {
    RealmResource realm1 = keycloak.realm(keycloakProperties.getRealm());
    return realm1.users();
  }

  @Override
  public UserRepresentation getUserById(String userId) {
    return getUsersResource().get(userId).toRepresentation();
  }

  @Override
  public void deleteUserById(String userId) {
    getUsersResource().delete(userId).close();
  }

  @Override
  public void emailVerification(String userId) {
    getUsersResource().get(userId).sendVerifyEmail();
  }

  @Override
  public UserResource getUserResource(String userId) {
    return getUsersResource().get(userId);
  }
}
