package com.example.authen.user;

import com.example.authen.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserEntity> get() {
        List<UserEntity> list = userRepository.findAll();
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity data = userRepository.findAllByUserName(userName);
        if(data == null){
            throw new UsernameNotFoundException(userName);
        }
        return new User(data.getUserName(),data.getPassword(),
                true,true,true,true, new ArrayList<>());
    }
}
