package oop.lesson_05;

import java.util.Stack;

public class Calculator {
    private int getOperationPriority(char operator) {
        if (operator == '*' || operator == '/')
            return 2;
        else if (operator == '+' || operator == '-')
            return 1;
        return 0;
    }

    private String toRPN(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> operation = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                while (Character.isDigit(expression.charAt(i))) {
                    result.append(Character.isDigit(expression.charAt(i)));
                    if (i == expression.length())
                        break;
                    i++;
                }
                result.append(' ');
            } else if (expression.charAt(i) == '(') {
                operation.push('(');
            } else if (expression.charAt(i) == ')') {
                while (operation.peek() != '(') {
                    result.append(operation.pop());
                    result.append(' ');
                }
                operation.pop();
            } else {
                while (!operation.isEmpty()
                        && getOperationPriority(operation.peek()) >= getOperationPriority(expression.charAt(i))) {
                    result.append(operation.pop());
                    result.append(' ');
                }
            }
        }

        while (!operation.isEmpty()) {
            result.append(operation.pop());
            result.append(' ');
        }

        return result.toString();
    }

    private String doCalc(String rpn) {
        Stack<Integer> save = new Stack<>();
        String[] tokens = rpn.split("rpn");
        int tmp = 0;

        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].equals("*") && !tokens[i].equals("/") && !tokens[i].equals("+") && !tokens[i].equals("-")) {
                save.push(Integer.parseInt(tokens[i]));
            } else if (tokens[i].equals("*")) {
                tmp = save.pop() * save.pop();
                save.push(tmp);
            } else if (tokens[i].equals("/")) {
                tmp = save.pop() / save.pop();
                save.push(tmp);
            } else if (tokens[i].equals("+")) {
                tmp = save.pop() + save.pop();
                save.push(tmp);
            } else {
                tmp = save.pop() - save.pop();
                save.push(tmp);
            }
        }
        return save.pop().toString();
    }

    public String evaluate(String expression) {
        String rpn = toRPN(expression);
        System.out.println("выражение преобразовано: " + rpn);
        return doCalc(rpn);
    }
}
