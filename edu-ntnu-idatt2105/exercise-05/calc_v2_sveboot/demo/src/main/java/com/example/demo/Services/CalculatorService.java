package com.example.demo.Services;

import com.example.demo.Models.CalculatorModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.logging.Logger;

@Service
@CrossOrigin
public class CalculatorService {

    private static final Logger logger = Logger.getLogger("Calculator Service Log");

    public String calculate(CalculatorModel calculatorModel) {
        logger.info("Starting calculation for query: " + calculatorModel.getExp());
        String[] parts = calculatorModel.getExp().split("[\\+\\-\\*/]");
        double num1 = Double.parseDouble(parts[0].trim());
        double num2 = Double.parseDouble(parts[1].trim());
        char operator = calculatorModel.getExp().replaceAll("[^\\+\\-\\*/]", "").charAt(0);

        double result = 0.0;

        switch (operator) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> {
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    throw new ArithmeticException("Cannot divide by zero");
                }
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        logger.info("Calculation success for query: " + calculatorModel.getExp() + " With result: " + result);
        return String.valueOf(result);
    }
}

