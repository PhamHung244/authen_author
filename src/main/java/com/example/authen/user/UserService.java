package com.example.authen.user;

import com.example.authen.In.LoginRequest;
import com.example.authen.In.RegisterRequest;
import com.example.authen.dtoResponse.AuthenResponse;
import com.example.authen.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<UserEntity> get();

    AuthenResponse register(RegisterRequest request);

    AuthenResponse login(LoginRequest request);
}
