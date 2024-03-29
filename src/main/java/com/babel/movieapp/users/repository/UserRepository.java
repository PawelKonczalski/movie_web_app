package com.babel.movieapp.users.repository;

import com.babel.movieapp.users.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsername(String username);
}
