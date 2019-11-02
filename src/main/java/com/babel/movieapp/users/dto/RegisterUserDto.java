package com.babel.movieapp.users.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String password;
    private String confirmPassword;
    private String username;

    public boolean passwordsMatch() {
        return password.equals(confirmPassword);
    }
}
