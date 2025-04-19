package edu.backend.model;

import jakarta.persistence.*;

@Entity
public class Calculation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String formula;
  private String result;

  @ManyToOne
  @JoinColumn(name = "app_user_id")
  private AppUser appUser;

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public AppUser getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }
}
