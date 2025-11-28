package com.leojcl.todos.service;

import com.leojcl.todos.request.AuthenticationRequest;
import com.leojcl.todos.request.RegisterRequest;
import com.leojcl.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
