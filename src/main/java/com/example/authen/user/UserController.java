package com.example.authen.user;

import com.example.authen.In.LoginRequest;
import com.example.authen.In.RegisterRequest;
import com.example.authen.dtoResponse.AuthenResponse;
import com.example.authen.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserEntity> getAll(){
        return userService.get();
    }

    @PostMapping
    public AuthenResponse registerUser(@RequestBody RegisterRequest request){
        return userService.register(request);
    }
    @PostMapping("/login")
    public AuthenResponse login(@RequestBody LoginRequest request){
        return userService.login(request);
    }
}
