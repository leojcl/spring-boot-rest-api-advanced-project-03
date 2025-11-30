package com.leojcl.todos.repository;

import com.leojcl.todos.entity.Todo;
import com.leojcl.todos.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByOwner(User user);
}
