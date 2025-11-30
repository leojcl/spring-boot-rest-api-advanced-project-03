package com.leojcl.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {


    @NotEmpty(message = "Old Password is mandatory")
    @Size(min = 8, max = 30, message = "Old Password must be at least 8 characters long")
    private String oldPassword;

    @NotEmpty(message = "New Password is mandatory")
    @Size(min = 8, max = 30, message = "New Password must be at least 8 characters long")
    private String newPassword;

    @NotEmpty(message = "Confirmed password is mandatory")
    @Size(min = 8, max = 30, message = "Confirmed password must be at least 8 characters long")
    private String passwordConfirm;

    public PasswordUpdateRequest(String oldPassword, String newPassword, String passwordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.passwordConfirm = passwordConfirm;
    }
}
