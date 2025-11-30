package com.leojcl.todos.service;

import com.leojcl.todos.request.PasswordUpdateRequest;
import com.leojcl.todos.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
