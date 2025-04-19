package com.example.demo.Controllers;

import com.example.demo.Services.LoggingService;
import com.example.demo.dto.LoggingRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class LoggingController {

    private final LoggingService loggingService;

    @CrossOrigin
    @PostMapping("/Logg")
    public void log(@RequestBody String expression){
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggingService.LoggEquation(expression, user);
    }

    @CrossOrigin
    @PostMapping("/GetLogg")
    public List<String> getLog(@RequestBody String name){
        System.out.println(name);
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggingService.getLog(user);
    }

}
