package com.example.demo.Controllers;

import com.example.demo.Models.CalculatorModel;
import com.example.demo.Services.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculate")
    public String calculate(@RequestBody String exp) {
        CalculatorModel calculatorModel = new CalculatorModel(exp);
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Request received");
        return calculatorService.calculate(calculatorModel);
    }
}
