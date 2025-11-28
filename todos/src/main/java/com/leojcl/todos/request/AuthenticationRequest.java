package com.leojcl.todos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 5, max = 30, message = "Password must be at least 5 characters long")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
