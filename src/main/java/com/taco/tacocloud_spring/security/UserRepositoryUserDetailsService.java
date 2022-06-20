package com.taco.tacocloud_spring.security;

import com.taco.tacocloud_spring.User;
import com.taco.tacocloud_spring.data.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
  private UserRepository repo;

  public UserRepositoryUserDetailsService(UserRepository repo) {
    this.repo = repo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repo.findByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UsernameNotFoundException("User" + user + "not found");
  }
}
