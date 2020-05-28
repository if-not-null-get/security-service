package com.adamantstudies.authenticationapp.security;

class JwtUser {

    private String username;
    private String password;

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
