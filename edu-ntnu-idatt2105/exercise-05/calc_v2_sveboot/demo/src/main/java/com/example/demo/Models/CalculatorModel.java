package com.example.demo.Models;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
public class CalculatorModel {
    private String exp;
    private String res;

    public CalculatorModel(@RequestBody String exp){
        this.exp = exp;
    }

    public String getExp(){
        return exp;
    }

    public String getRes() {
        return res;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
