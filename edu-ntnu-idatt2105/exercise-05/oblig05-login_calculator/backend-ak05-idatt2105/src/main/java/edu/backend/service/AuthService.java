package edu.backend.service;

import edu.backend.model.AppUser;
import edu.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void authenticate(String username, String password) throws Exception {
    AppUser appUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new Exception("Invalid username/password"));
    if (!passwordEncoder.matches(password, appUser.getPassword())) {
      throw new Exception("Invalid username/password");
    }
  }

  public AppUser registerUser(AppUser newAppUser) throws Exception {
    if(userRepository.existsByUsername(newAppUser.getUsername())) {
      throw new Exception("Username already taken");
    }
    newAppUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));
    return userRepository.save(newAppUser);
  }
}
