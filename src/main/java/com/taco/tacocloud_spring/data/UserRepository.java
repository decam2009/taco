package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername (String username);
}
