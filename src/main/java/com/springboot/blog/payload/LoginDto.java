package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotEmpty(message = "Username or Email should not be empty")
    private String usernameOrEmail;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
