package com.soomee.notesjwt.service;

import com.soomee.notesjwt.dto.request.LoginRequest;
import com.soomee.notesjwt.dto.request.SignupRequest;
import com.soomee.notesjwt.dto.response.JwtResponse;
import com.soomee.notesjwt.dto.response.MessageResponse;

public interface UserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);
}
