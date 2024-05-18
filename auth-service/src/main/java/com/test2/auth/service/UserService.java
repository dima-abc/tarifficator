package com.test2.auth.service;

import com.test2.auth.model.LoginRequestDto;
import com.test2.auth.model.LoginResponseDto;
import com.test2.auth.model.SignUpDto;

public interface UserService {
    SignUpDto signUp(SignUpDto signUpDto);

    LoginResponseDto login(LoginRequestDto loginRequest);
}
