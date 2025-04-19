package edu.backend.controller;

import edu.backend.model.CalculationRequest;
import edu.backend.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

  // Defining a logger instance
  private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

  @Autowired
  private CalculatorService calculatorService;

  /**
   * Creates a CalculationRequest object.
   * The calculationResult object from calculatorService is "put inside" this object
   * as it is received from the POST request from web -> calculateResult.
   *
   * Calls CalculatorService/calculate method on the object.
   * When the calculation has been avaluated, the result object is sent back to the
   * web frontend via ResponseEntity and the "ok" status.
   *
   * @param request
   * @return
   */
  @PostMapping("/calculate")
  public ResponseEntity<?> calculate(@RequestBody CalculationRequest request) {
    try {
      double result = calculatorService.calculate(request.getFormula());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      // Using the logger instance to log errors
      logger.error("Error calculating formula: {}", e.getMessage());
      // Using HttpStatus enum for the status code
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calculating formula");
    }
  }
}
