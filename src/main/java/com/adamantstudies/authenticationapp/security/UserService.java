package com.adamantstudies.authenticationapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser saveUser(AppUser appUser) {
        return userRepository.save(appUser);
    }
}
