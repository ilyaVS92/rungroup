package com.example.rungroup.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "Username must be filled in")
    @Size(min = 2, message="Username must be > 2 chars")
    private String userName;
    @NotEmpty(message = "User's email cannot be empty.")
    @Email(message="Email must be properly formatted")
    private String email;
    @NotEmpty(message="must provide a password")
    private String password;
    @NotEmpty
    private String matchingPassword;
}
