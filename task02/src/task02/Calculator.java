package task02;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {
  public static void main(String[] args) {
    System.out.println("Welcome.");
    
    try (Scanner scanner = new Scanner(System.in)) {
      double last = 0;
      Stack<Double> operands = new Stack<>();
      Stack<String> operators = new Stack<>();

      // Prompt the user to enter the expression
      while (true) {
        System.out.print("> ");
        String expression = scanner.nextLine();
        
        String[] inputs = expression.split(" ");

        for (int i = 0; i < inputs.length; i++ ) {
          if (inputs[i].equals("$last")) {
            operands.push(last);
          }
          else if (inputs[i].equals("+") || inputs[i].equals("-") || 
                      inputs[i].equals("*") || inputs[i].equals("/")) {
            operators.push(inputs[i]);
          }
          else if (inputs[i].equals("exit")) {
            closeApp();
          }
          else {
            operands.push(Double.parseDouble(inputs[i]));
          }
        }

        while (!operators.isEmpty()) {
          String operator = operators.pop();
          double operand2 = operands.pop();
          double operand1 = operands.pop();
          operands.push(evaluate(operator, operand1, operand2));
        }

        last = operands.pop();
        System.out.println(last);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  // Evaluates the given operator on the given operands
  private static double evaluate(String operator, double operand1, double operand2) {
    switch (operator) {
      case "+":
        return operand1 + operand2;
      case "-":
        return operand1 - operand2;
      case "*":
        return operand1 * operand2;
      case "/":
        return operand1 / operand2;
      default:
        throw new IllegalArgumentException("Invalid operator");
    }
  }

  private static void closeApp() {
    System.out.println("Bye bye");
    System.exit(1);
  }
}
