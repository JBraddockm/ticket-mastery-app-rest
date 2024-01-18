package org.example.service.impl;

import java.util.Collections;
import java.util.List;
import org.example.dto.KeycloakProperties;
import org.example.dto.RoleDTO;
import org.example.exception.RoleNotFoundException;
import org.example.mapper.RoleMapper;
import org.example.repository.RoleRepository;
import org.example.service.KeycloakUserService;
import org.example.service.RoleService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final KeycloakProperties keycloakProperties;

  private final Keycloak keycloak;

  private final KeycloakUserService keycloakUserService;

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleServiceImpl(
      KeycloakProperties keycloakProperties,
      Keycloak keycloak,
      KeycloakUserService keycloakUserService,
      RoleRepository roleRepository,
      RoleMapper roleMapper) {
    this.keycloakProperties = keycloakProperties;
    this.keycloak = keycloak;
    this.keycloakUserService = keycloakUserService;
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }

  @Override
  public List<RoleDTO> findAll() {
    return roleRepository.findAll().stream().map(roleMapper::mapToDTO).toList();
  }

  @Override
  public RoleDTO findById(Long id) {
    return roleMapper.mapToDTO(
        roleRepository
            .findById(id)
            .orElseThrow(() -> new RoleNotFoundException(String.valueOf(id))));
  }

  @Override
  public void assignRole(String userId, String roleName) {
    UserResource userResource = keycloakUserService.getUserResource(userId);
    RolesResource rolesResource = getRolesResource();
    RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
    userResource.roles().realmLevel().add(Collections.singletonList(representation));
  }

  private RolesResource getRolesResource() {
    return keycloak.realm(keycloakProperties.getRealm()).roles();
  }
}
