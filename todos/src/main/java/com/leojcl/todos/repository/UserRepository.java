package com.leojcl.todos.repository;

import com.leojcl.todos.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
    Optional<User> findByEmail(String email);

}
