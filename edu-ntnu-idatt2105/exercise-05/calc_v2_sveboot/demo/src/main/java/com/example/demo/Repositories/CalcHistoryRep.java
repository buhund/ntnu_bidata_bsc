package com.example.demo.Repositories;

import com.example.demo.Enteties.CalcHistoryEntity;
import com.example.demo.Enteties.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalcHistoryRep extends JpaRepository<CalcHistoryEntity, Long> {
    CalcHistoryEntity findCalcHistoryEntitiesByUserEntity(User user);

    List<CalcHistoryEntity> findByUserEntityId(Long userEntityId);
}
