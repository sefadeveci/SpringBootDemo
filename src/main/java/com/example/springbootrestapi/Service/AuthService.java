package com.example.springbootrestapi.Service;

import com.example.springbootrestapi.DTO.LoginDTO;
import com.example.springbootrestapi.DTO.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
