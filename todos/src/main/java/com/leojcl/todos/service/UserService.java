package com.leojcl.todos.service;

import com.leojcl.todos.entity.User;
import com.leojcl.todos.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
}
