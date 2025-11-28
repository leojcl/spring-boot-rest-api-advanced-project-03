package com.leojcl.todos.service;

import com.leojcl.todos.entity.User;
import com.leojcl.todos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymouseUser "){
            throw new AccessDeniedException("Authentication required");
        }
        return (User) authentication.getPrincipal();
    }
}
