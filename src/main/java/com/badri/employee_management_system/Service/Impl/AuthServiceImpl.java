package com.badri.employee_management_system.Service.Impl;


import com.badri.employee_management_system.Dto.RegisterRequestDto;
import com.badri.employee_management_system.Entity.Role;
import com.badri.employee_management_system.Entity.User;
import com.badri.employee_management_system.Repository.UserRepository;
import com.badri.employee_management_system.Security.JwtService;
import com.badri.employee_management_system.Service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager ;

    private final JwtService jwtService ;

    private UserRepository userRepository ;

    private PasswordEncoder passwordEncoder;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String username, String password) {

     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username ,password));
     return jwtService.generateToken(username);
    }

    @Override
    public void register(RegisterRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String encodedpassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedpassword);
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
//
//    private final UserRepository userRepository ;
//
//    private final PasswordEncoder passwordEncoder ;
//
//    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }