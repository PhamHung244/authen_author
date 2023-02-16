package com.example.authen.user;

import com.example.authen.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<UserEntity> get();
}
