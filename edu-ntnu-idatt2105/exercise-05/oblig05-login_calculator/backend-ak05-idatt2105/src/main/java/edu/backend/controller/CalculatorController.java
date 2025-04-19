package edu.backend.controller;

import edu.backend.model.Calculation;
import edu.backend.model.AppUser;
import edu.backend.repository.CalculationRepository;
import edu.backend.repository.UserRepository;
import edu.backend.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

  private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

  @Autowired
  private CalculatorService calculatorService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CalculationRepository calculationRepository;

  @PostMapping("/calculate")
  public ResponseEntity<?> calculate(@RequestBody Calculation calculation, Principal principal) {
    String username = principal.getName();
    AppUser currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    calculation.setAppUser(currentUser);
    double result = calculatorService.calculate(calculation.getFormula());
    calculation.setResult(String.valueOf(result));
    Calculation savedCalculation = calculationRepository.save(calculation);
    return ResponseEntity.ok(savedCalculation);
  }

  @GetMapping("/calculations")
  public ResponseEntity<Page<Calculation>> getCalculations(Pageable pageable, Principal principal) {
    String username = principal.getName();
    AppUser currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Page<Calculation> calculations = calculationRepository.findByAppUserId(currentUser.getId(), pageable);
    return ResponseEntity.ok(calculations);
  }

  @PostMapping("/calculations")
  public ResponseEntity<?> saveCalculation(@RequestBody Calculation calculation, Principal principal) {
    // Retrieve the username from the Principal object
    String username = principal.getName();

    // Find the user by username and handle the Optional
    AppUser user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Set the user to the calculation
    calculation.setAppUser(user);

    // Save the calculation
    Calculation savedCalculation = calculationRepository.save(calculation);

    return ResponseEntity.ok(savedCalculation);
  }




  private Optional<AppUser> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    return userRepository.findByUsername(currentUsername);
  }

}
