package com.soomee.notesjwt.controller;

import com.soomee.notesjwt.model.User;
import com.soomee.notesjwt.model.request.LoginRequest;
import com.soomee.notesjwt.model.request.SignupRequest;
import com.soomee.notesjwt.model.response.MessageResponse;
import com.soomee.notesjwt.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        MessageResponse response = userService.registerUser(signUpRequest);
        if (response.getIsError())
            return ResponseEntity
                    .badRequest()
                    .body(response.getMessage());
        else
            return ResponseEntity.ok(response.getMessage());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
