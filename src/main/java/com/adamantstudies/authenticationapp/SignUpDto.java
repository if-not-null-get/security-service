package com.adamantstudies.authenticationapp;

public class SignUpDto {
    public final String username;
    public final String password;

    public SignUpDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
