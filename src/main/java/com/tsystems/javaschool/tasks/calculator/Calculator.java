package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.evaluate("(1+38)*4-5"));
        System.out.println(calculator.evaluate("7*6/2+8"));
        System.out.println(calculator.evaluate("-12)1//("));
    }

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    @SuppressWarnings("rawtypes")
    public String evaluate(String statement) {

        Stack<String> numbersStack = new Stack<>();
        Stack<String> signsStack = new Stack<>();
        ArrayList<String> expression = stringToArray(statement);
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        try {
            for (String s : expression) {
                if (s.equals("")) {
                    while (numbersStack.size() != 1) {
                        performOperation(numbersStack, signsStack);
                    }
                } else if (isNumber(s)) {
                    numbersStack.push(s);
                } else if ((signsStack.isEmpty() || operationPriority(s) > operationPriority(signsStack.peek())
                        || signsStack.peek().equals("(")) && !s.equals(")")) {
                    signsStack.push(s);
                } else if (operationPriority(s) <= operationPriority(signsStack.peek()) && !s.equals(")")) {
                    performOperation(numbersStack, signsStack);
                    while(!signsStack.isEmpty() && operationPriority(s) == operationPriority(signsStack.peek())) {
                        performOperation(numbersStack, signsStack);
                    }
                    signsStack.push(s);
                } else if (s.equals(")")) {
                    while (!signsStack.peek().equals("(")) {
                        performOperation(numbersStack, signsStack);
                    }
                    signsStack.pop();
                }
            }
        } catch (Exception e) {
            return null;
        }

        if (!signsStack.isEmpty()) {
            return null;
        }
        return decimalFormat.format(Double.parseDouble(numbersStack.pop()));
    }

    /**
     * Converts expression to list of separated items (signs and numbers)
     * @param expression
     * @return list with signs and numbers in the given order
     */
    private static ArrayList<String> stringToArray(String expression) {
        if (expression == null || expression.equals("") ||
                expression.contains("..") || expression.contains(",")) return null;
        Pattern p = Pattern.compile("[0-9.]+|[+-/*()]?");
        Matcher m = p.matcher(expression);
        ArrayList<String> parsedExprrssion = new ArrayList<>();
        while (m.find()) {
            parsedExprrssion.add(m.group());
        }
        return parsedExprrssion;
    }

    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int operationPriority(String sign) {
        switch (sign) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return 4;
    }

    private static double calculateTwoItems(double a, double b, String sign) {
        switch (sign) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
        }
      return -1;
    }

    /**
     * Performs calculation and puts the result in stack of numbers
     * Deletes used operator from stack of signs
     * @param num stack of numbers
     * @param sign stack of signs (operators and parentheses)
     * @throws ArithmeticException to prevent devision by zero
     */
    private static void performOperation(Stack<String> num, Stack<String> sign) throws ArithmeticException {
        double second = Double.parseDouble(num.pop());
        if (second == 0) {
            throw new ArithmeticException();
        }
        double first = Double.parseDouble(num.pop());
        num.push(Double.toString(calculateTwoItems(first, second, sign.peek())));
        sign.pop();
    }
}
