package com.leojcl.todos.controller;

import com.leojcl.todos.request.PasswordUpdateRequest;
import com.leojcl.todos.response.UserResponse;
import com.leojcl.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name="User REST API Endpoint", description = "operation related to into about current user ")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo(){
        return  userService.getUserInfo();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteUser(){
        userService.deleteUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password")
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest){
        userService.updatePassword(passwordUpdateRequest);
    }
}
