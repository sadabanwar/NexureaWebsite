package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 3, message = "Username should have at least 3 characters")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 15, message = "Phone should be between 10 to 15 characters")
    private String phone;

    private String referredBy;
}
