package com.leojcl.todos.response;

import com.leojcl.todos.entity.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private long id;
    private String fullName;
    private String email;
    private List<Authority> authorities;

    public UserResponse(long id, String fullName, String email, List<Authority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorities = authorities;
    }
}
