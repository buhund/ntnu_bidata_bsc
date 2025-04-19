package com.example.demo.Services;

import com.example.demo.Enteties.UserEntity;
import com.example.demo.Repositories.UserEntityRep;
import com.example.demo.dto.LoginRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin
public class LogInService {

    private final UserEntityRep userEntityRep;

    public LogInService(UserEntityRep userEntityRep){
        this.userEntityRep = userEntityRep;
    }


    public String validate(LoginRequestDTO loginRequestDTO){
        UserEntity user = userEntityRep.findUserEntityByName(loginRequestDTO.getUserName());
        try{
            if(user.getPassword().equals(loginRequestDTO.getPassword())){
                TokenService tokenService = new TokenService();
                return tokenService.createToken(loginRequestDTO.getUserName());
        }
        }catch (Exception e){
            return "failed";
        }
        return "failed";
    }
}
