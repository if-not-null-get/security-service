package com.adamantstudies.authenticationapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(BCryptPasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user with given username found!")
                );
        var authorities = new HashSet<GrantedAuthority>();
        for(String role: user.getAuthorities()){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
