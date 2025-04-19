package com.example.demo.Services;

import com.example.demo.Enteties.CalcHistoryEntity;
import com.example.demo.Enteties.UserEntity;
import com.example.demo.Repositories.CalcHistoryRep;
import com.example.demo.Repositories.UserEntityRep;
import com.example.demo.dto.LoggingRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin
public class LoggingService {

    private final CalcHistoryRep calcHistoryRep;
    private final UserEntityRep userEntityRep;

    public LoggingService(CalcHistoryRep calcHistoryRep, UserEntityRep userEntityRep) {
        this.calcHistoryRep = calcHistoryRep;
        this.userEntityRep = userEntityRep;
    }

    public void LoggEquation(String exp, String name){

        UserEntity user = userEntityRep.findUserEntityByName(name);
        System.out.println(exp);

        if (user != null) {
            CalcHistoryEntity calcHistoryEntity = new CalcHistoryEntity();

            calcHistoryEntity.setEquation(exp);

            calcHistoryEntity.setUserEntity(user);

            calcHistoryRep.save(calcHistoryEntity);
        } else {
            // Handle case where user with the given ID is not found
        }
    }

    public List<String> getLog(String name) {
        UserEntity user = userEntityRep.findUserEntityByName(name);
        if (user != null) {
            List<CalcHistoryEntity> entities = calcHistoryRep.findByUserEntityId(user.getId());
            List<String> userLog = new ArrayList<>();
            for(CalcHistoryEntity e : entities){
                userLog.add(e.getEquation());
            }
            return userLog;
        } else {
            // Handle case where user is not found
            return null;
        }
    }
}
