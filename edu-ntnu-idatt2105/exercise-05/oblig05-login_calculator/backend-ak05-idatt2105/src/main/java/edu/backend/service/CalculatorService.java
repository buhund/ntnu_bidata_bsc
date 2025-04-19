package edu.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Service
public class CalculatorService {
  private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);
  private ScriptEngine engine;

  /**
   * Since the engine is set to "lazy" initialization, the constructor is kept empty.
   * Also, with Spring it will be automatically instantiates as a "bean", due to
   * the `@Service´ class annotation, and Java will provide a default no-argument
   * constructor for Spring.
   */
  public CalculatorService() {

  }

  private ScriptEngine getEngine() {
    if (engine == null) {
      engine = new ScriptEngineManager().getEngineByName("Graal.js");
      if (engine == null) {
        logger.error("ScriptEngine has not been initialized. JavaScript engine is not available.");
        throw new IllegalStateException("ScriptEngine has not been initialized. JavaScript engine is not available.");
      }
    }
    return engine;
  }

  /**
   * Calculate the result of a binary operation.
   * Not in use. Sending the calculation as a string is easier,
   * and then just parse the string instead of individual operators.
   *
   * @param operand1 the first operand
   * @param operand2 the second operand
   * @param operator the operator (+, -, *, /)
   * @return the result of the operation
   */
  public double calculate(double operand1, double operand2, String operator) {
    switch (operator) {
      case "+":
        return operand1 + operand2;
      case "-":
        return operand1 - operand2;
      case "*":
        return operand1 * operand2;
      case "/":
        if (operand2 == 0) throw new IllegalArgumentException("Division by zero.");
        return operand1 / operand2;
      default:
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }
  }

  /**
   * Evaluate a math expression given as a string.
   *
   * Invoked on a CalculationRequest object, containing the original formula from web.
   * Uses the script engine to evaluate the formula object into a number.
   * -- The script engine evaluation breaks it down, and evaluates the numerical characters
   *    and known mathematical operators as such.
   * -- Then further evaluates that, based on know patterns, this is most likely a
   *    calculation and then evaluates it as a mathematical expression.
   * -- The resulting number of the evaluation/calculation is then returned.
   *
   * @param formula the mathematical expression to evaluate
   * @return the result of the expression
   */
  public double calculate(String formula) {
    logger.info("Received formula for evaluation: {}", formula);
    try {
      Object result = getEngine().eval(formula);
      if (result instanceof Number) {
        return ((Number) result).doubleValue();
      } else {
        logger.error("Evaluation did not result in a number: {}", result);
        throw new ScriptException("Result is not a number");
      }
    } catch (ScriptException e) {
      logger.error("Error evaluating formula: {}", e.getMessage());
      throw new IllegalArgumentException("Error evaluating formula: " + formula, e);
    }
  }
}

