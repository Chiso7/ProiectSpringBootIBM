package com.example.SpringBoot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Name cannot be null!")
    private String name;

    @NotEmpty(message = "Email cannot be null!")
    @Email
    private String email;

    @NotEmpty(message = "You must enter a password!")
    private String password;

    private String role;
}
