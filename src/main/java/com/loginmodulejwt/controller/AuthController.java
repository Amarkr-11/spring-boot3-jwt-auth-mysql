package com.loginmodulejwt.controller;

import com.loginmodulejwt.dto.*;
import com.loginmodulejwt.model.Role;
import com.loginmodulejwt.model.User;
import com.loginmodulejwt.repository.UserRepository;
import com.loginmodulejwt.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {


    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, UserRepository userRepo,
                          PasswordEncoder encoder, JwtService jwtService) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

   /* @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) return ResponseEntity.badRequest().build();
        if (userRepo.existsByEmail(req.email())) return ResponseEntity.badRequest().build();

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }*/

    //updated register response after registering the user

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) return ResponseEntity.badRequest().build();
        if (userRepo.existsByEmail(req.email())) return ResponseEntity.badRequest().build();

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .role(Role.USER) // always USER
                .build();

        userRepo.save(user);
        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new RegisterResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), token)
        );
    }


    //Admin user registration
    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> registerByAdmin(@Valid @RequestBody AdminRegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) return ResponseEntity.badRequest().build();
        if (userRepo.existsByEmail(req.email())) return ResponseEntity.badRequest().build();

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .role(req.role()) // admin can set any role
                .build();

        userRepo.save(user);
        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new RegisterResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), token)
        );
    }




    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String token = jwtService.generateToken(req.username());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
