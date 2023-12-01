package org.example.service.impl;


import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.model.common.UserPrincipal;
import org.example.repository.UserRepository;
import org.example.service.DatabaseUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DatabaseUserDetailsServiceImpl implements DatabaseUserDetailsService {

  private final UserRepository userRepository;

  public DatabaseUserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));

    return new UserPrincipal(user);
  }
}
