package com.example.authen.user;

import com.example.authen.In.LoginRequest;
import com.example.authen.In.RegisterRequest;
import com.example.authen.dtoResponse.AuthenResponse;
import com.example.authen.dtoResponse.RegisterDto;
import com.example.authen.jwt.JwtUltity;
import com.example.authen.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUltity jwtUltity;
    @Override
    public List<UserEntity> get() {
        List<UserEntity> list = userRepository.findAll();
        return list;
    }

    @Override
    public AuthenResponse register(RegisterRequest request) {
        if(request.getUserName() == null || request.getPassWord()== null){
            return new AuthenResponse("Vui lòng truyền đầy đủ user name và password");
        }
        UserEntity checkUserName = userRepository.findAllByUserName(request.getUserName());
        if (checkUserName != null){
            return new AuthenResponse("User Name đã tồn tại");
        }
//        UserEntity userEntity = userRepository.findAllByUserName(request.getUserName());
        UserEntity userEntity = setUpData(request);
        UserEntity data = userRepository.save(userEntity);
        RegisterDto dto = new RegisterDto();
        dto.setId(data.getId());
        dto.setUserName(data.getUserName());

        return new AuthenResponse("Đăng ký thành công", dto, null);

    }

    @Override
    public AuthenResponse login(LoginRequest request) {
        if(request.getUserName() == null || request.getPassWord()== null){
            return new AuthenResponse("Vui lòng truyền đầy đủ user name và password");
        }
        UserEntity checkUserName = userRepository.findAllByUserName(request.getUserName());
        if (checkUserName == null){
            return new AuthenResponse("User Name không tồn tại");
        }
        if(!BCrypt.checkpw(request.getPassWord(), checkUserName.getPassword())){
            return new AuthenResponse("Vui lòng xem lại mật khẩu");
        }else{
            String token = jwtUltity.generateJwtToken(checkUserName.getUserName());
            return new AuthenResponse("đăng nhập thành công", checkUserName, token);
        }

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

    private UserEntity setUpData(RegisterRequest request){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(request.getUserName());
        userEntity.setPassword(BCrypt.hashpw(request.getPassWord(),BCrypt.gensalt(10)));
        return userEntity;
    }
}
