package com.north.poc.controller;

import com.north.poc.dto.UserDto;
import com.north.poc.model.User;
import com.north.poc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping("/user")
    public UserDto getUserByToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        return userService.getUserById(userId);
    }
}
