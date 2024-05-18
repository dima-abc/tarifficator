package com.test2.auth.controller;

import com.test2.auth.model.LoginRequestDto;
import com.test2.auth.model.LoginResponseDto;
import com.test2.auth.model.SignUpDto;
import com.test2.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignUpDto> signUp(@Valid @RequestBody SignUpDto signUpRequest) {
        return ResponseEntity.ok(this.userService.signUp(signUpRequest));
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto) {
        LoginResponseDto login = this.userService.login(loginDto);
        return ResponseEntity.ok(login);
    }

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<ProblemDetail> handelNotFound(HttpResponseException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }
}
