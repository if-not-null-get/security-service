package com.adamantstudies.authenticationapp;

import com.adamantstudies.authenticationapp.security.AppUser;
import com.adamantstudies.authenticationapp.security.AppUserDetailsService;
import com.adamantstudies.authenticationapp.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class LoginController {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private AppUserDetailsService userDetailsService;

    @Autowired
    public LoginController(UserService userService,
                           BCryptPasswordEncoder passwordEncoder,
                           AppUserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        UserDetails principal = userDetailsService.loadUserByUsername(loginDto.username);
        Authentication token = new UsernamePasswordAuthenticationToken(principal.getUsername(),
                principal.getPassword(),
                principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        AppUser newUser = new AppUser(signUpDto.username, passwordEncoder.encode(signUpDto.password), Set.of("ROLE_USER"));
        AppUser result = userService.saveUser(newUser);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/home")
    public String welcome() {
        return "Hi from get!";
    }


}
