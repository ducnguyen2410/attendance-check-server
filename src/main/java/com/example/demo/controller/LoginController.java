package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.payload.UserPayload;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String welcome() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserPayload userPayload) {
        try {
            UserDto user = userService.createUser(userPayload);
            return ResponseEntity.status(200).body(user);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Registration failed.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserPayload userPayload) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPayload.getUsername(), userPayload.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(userPayload.getUsername());
        String jwtToken = jwtUtil.generateToken(user);
        return ResponseEntity.ok(Map.of("token", jwtToken,
                "username", user.getUsername(),
                "roles", user.getRoles().stream().map(Role::getName).toList()));
    }
}
