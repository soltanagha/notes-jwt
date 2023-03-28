package com.soomee.notesjwt.service;

import com.soomee.notesjwt.model.request.LoginRequest;
import com.soomee.notesjwt.model.request.SignupRequest;
import com.soomee.notesjwt.model.response.JwtResponse;
import com.soomee.notesjwt.model.response.MessageResponse;

public interface UserService {

    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);
}
