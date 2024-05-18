package com.test2.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString
public class SignUpDto {
    @NotBlank(message = "create user errors username is null")
    private String username;
    @NotBlank(message = "create user errors email is null")
    private String email;
    @NotBlank(message = "create user errors firstname is null")
    private String firstname;
    @NotBlank(message = "create user errors lastname is null")
    private String lastname;
    @NotBlank(message = "create user errors password is null")
    private String password;
    private int statusCode;
    private String statusMessage;
}
