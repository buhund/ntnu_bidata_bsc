package edu.backend.controller;

import edu.backend.model.AppUser;
import edu.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import edu.backend.model.Calculation;
import edu.backend.repository.CalculationRepository;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepository userRepository;
  private final CalculationRepository calculationRepository;

  public UserController(UserRepository userRepository, CalculationRepository calculationRepository) {
    this.userRepository = userRepository;
    this.calculationRepository = calculationRepository;
  }

  @PostMapping
  public AppUser createUser(@RequestBody AppUser appUser) {
    return userRepository.save(appUser);
  }

  @PostMapping("/{id}/calculations")
  public Calculation createCalculation(@PathVariable Long id, @RequestBody Calculation calculation) {
    AppUser appUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("AppUser not found"));
    calculation.setAppUser(appUser); // Use the appUser object obtained from the repository
    return calculationRepository.save(calculation);
  }


  @GetMapping("/{id}/calculations")
  public List<Calculation> getCalculations(@PathVariable Long id) {
    return calculationRepository.findByAppUserId(id);
  }
}
