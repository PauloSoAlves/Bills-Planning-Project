package com.billsplanning.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billsplanning.backend.DTO.Auth.UserRegisterRequestDTO;
import com.billsplanning.backend.DTO.Auth.UserRegisterResponseDTO;
import com.billsplanning.backend.entity.User;
import com.billsplanning.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO data) {
        
        User userEntity = new User();
        userEntity.setUsername(data.username());
        userEntity.setEmail(data.email());
        userEntity.setPassword(data.password());

        User savedUser = userService.registerUser(userEntity);

        UserRegisterResponseDTO response = new UserRegisterResponseDTO(
            savedUser.getUserId(), 
            savedUser.getUsername(), 
            savedUser.getEmail()
        );

        return ResponseEntity.ok(response);
    }
    
}
