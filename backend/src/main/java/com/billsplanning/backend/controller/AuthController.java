package com.billsplanning.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billsplanning.backend.DTO.Auth.UserLoginRequestDTO;
import com.billsplanning.backend.DTO.Auth.UserRegisterRequestDTO;
import com.billsplanning.backend.DTO.Auth.UserRegisterResponseDTO;
import com.billsplanning.backend.DTO.Auth.LoginResponseDTO;
import com.billsplanning.backend.entity.User;
import com.billsplanning.backend.service.TokenService;
import com.billsplanning.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO data) {
        
        User userEntity = new User();
        userEntity.setUsername(data.username());
        userEntity.setEmail(data.email());
        userEntity.setPassword(data.password());

        User savedUser = userService.registerUser(userEntity);

        UserRegisterResponseDTO response = new UserRegisterResponseDTO(savedUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginRequestDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO(
            user.getUserId(), 
            user.getUsername(), 
            user.getEmail(),
            token
        );

        return ResponseEntity.ok(response);  
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        String email = tokenService.validateToken(token);
        if (email.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        return ResponseEntity.ok("Token is valid for email: " + email);
    }
    
}
