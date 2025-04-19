package com.example.demo.Enteties;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import lombok.Getter;

@Getter
@Entity
@Table(name = "expressions")
public class CalcHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String equation;

    @ManyToOne
    @JoinColumn(name="user_entity_id")
    private UserEntity userEntity;

    public void setId(Long id){
        this.id=id;
    }

    public void setEquation(String equation){
        this.equation=equation;
    }

    public void setUserEntity(UserEntity userEntity){
        this.userEntity=userEntity;
    }
}
