package com.example.demo.Repositories;

import com.example.demo.Enteties.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEntityRep extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByName(String name);
}
