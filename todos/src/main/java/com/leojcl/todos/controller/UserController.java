package com.leojcl.todos.controller;

import com.leojcl.todos.entity.User;
import com.leojcl.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="User REST API Endpoint", description = "operation related to into about current user ")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public User getUserInfo(){
        return  userService.getUserInfo();
    }
}
