package com.example.demo.controller;

import com.example.demo.config.JwtConfig;
import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.payload.TokenPayload;
import com.example.demo.payload.UserPayload;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    @Autowired
    public LoginController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtConfig jwtConfig, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
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
        UserDto user = userService.getUserByUserName(userPayload.getUsername());
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        String redisKey = "refresh_token:" + user.getId();
        redisTemplate.opsForValue().set(redisKey, refreshToken, jwtConfig.getRefreshTokenExpiration());
        return ResponseEntity.ok(Map.of("accessToken", accessToken,
                "refreshToken", refreshToken,
                "username", user.getUsername(),
                "roles", user.getRoles().stream().map(RoleDto::getName).toList()));
    }

    @PostMapping("/access-token")
    public ResponseEntity<?> getAccessToken(@RequestBody TokenPayload tokenPayload) {
        try {
            Long userId = jwtUtil.getUserIdFromToken(tokenPayload.getRefreshToken());
            String redisKey = "refresh_token:" + userId;
            String refreshToken = redisTemplate.opsForValue().get(redisKey);
            if (refreshToken == null || !refreshToken.equals(tokenPayload.getRefreshToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
            }
            UserDto user = userService.getUserById(userId);
            String newAccessToken = jwtUtil.generateAccessToken(user);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }
    }
}
