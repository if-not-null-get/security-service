package com.adamantstudies.authenticationapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login() {
        return "Success";
    }

    @GetMapping("/home")
    public String welcome() {
        return "Hi from get!";
    }
}
