package edu.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_users")
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String username;
  private String password;
  @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
  private List<Calculation> calculations;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Calculation> getCalculations() {
    return calculations;
  }

  public void setCalculations(List<Calculation> calculations) {
    this.calculations = calculations;
  }
}