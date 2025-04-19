package com.example.demo.Controllers;

import com.example.demo.Services.LogInService;
import com.example.demo.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class LogInController {

    private final LogInService logInService;

    @PostMapping("/logIn")
    public String calculate(@RequestBody LoginRequestDTO loginRequest) {

        log.info("Request received");
        return logInService.validate(loginRequest);
    }
}
