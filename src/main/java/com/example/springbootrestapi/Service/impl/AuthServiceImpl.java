package com.example.springbootrestapi.Service.impl;

import com.example.springbootrestapi.DTO.LoginDTO;
import com.example.springbootrestapi.DTO.RegisterDTO;
import com.example.springbootrestapi.Entity.Role;
import com.example.springbootrestapi.Entity.User;
import com.example.springbootrestapi.Exception.ApiException;
import com.example.springbootrestapi.Repository.RoleRepository;
import com.example.springbootrestapi.Repository.UserRepository;
import com.example.springbootrestapi.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service

public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,RoleRepository roleRepository, PasswordEncoder passwordEncoder ) {
        this.authenticationManager = authenticationManager;
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login successfull";
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"This username exist");
        }
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"This email exist");
        }
        User user=new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Set<Role> roles=new HashSet<>();
        Role userRole=roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Save Succesfully";



    }
}
