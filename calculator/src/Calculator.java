import java.util.*;

import static java.lang.Double.NaN;
import static java.lang.Math.pow;


/*
 *   A calculator for rather simple arithmetic expressions
 *
 *   This is not the program, it's a class declaration (with methods) in it's
 *   own file (which must be named Calculator.java)
 *
 *   NOTE:
 *   - No negative numbers implemented
 */
public class Calculator {

    // Here are the only allowed instance variables!
    // Error messages (more on static later)
    final static String MISSING_OPERAND = "Missing or bad operand";
    final static String DIV_BY_ZERO = "Division with 0";
    final static String MISSING_OPERATOR = "Missing operator or parenthesis";
    final static String OP_NOT_FOUND = "Operator not found";

    // Definition of operators
    final static String OPERATORS = "+-*/^";

    // Method used in REPL
    double eval(String expr) {
        if (expr.length() == 0) {
            return NaN;
        }
        List<String> tokens = tokenize(expr);
        List<String> postfix = infix2Postfix(tokens);
        return evalPostfix(postfix);
    }

    // ------  Evaluate RPN expression -------------------

    double evalPostfix(List<String> postfix) {
        Deque<String> stack = new ArrayDeque<String>();
        for (String i : postfix) {
            if (!isOperator(i)){
                stack.push(i);

            } else {
                if (stack.size() >= 2){
                    double a = Double.valueOf(stack.pop());
                    double b = Double.valueOf(stack.pop());
                    stack.push(String.valueOf(applyOperator(i, a, b)));
                } else {
                    throw new IllegalArgumentException(MISSING_OPERAND);
                }
            }
        }

        if (stack.size() > 1){
            throw new IllegalArgumentException(MISSING_OPERATOR);
        }

        return Double.valueOf(stack.pop());
    }

    double applyOperator(String op, double d1, double d2) {
        switch (op) {
            case "+":
                return d1 + d2;
            case "-":
                return d2 - d1;
            case "*":
                return d1 * d2;
            case "/":
                if (d1 == 0) {
                    throw new IllegalArgumentException(DIV_BY_ZERO);
                }
                return d2 / d1;
            case "^":
                return pow(d2, d1);
        }
        throw new RuntimeException(OP_NOT_FOUND);
    }

    // ------- Infix 2 Postfix ------------------------

    List<String> infix2Postfix(List<String> infix) {
        List<String> postfix = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        for (String i : infix) {
            if (!isOperator(i)) {
                postfix.add(i);
            } else if ("(".equals(i)) {
                stack.push(i);
            } else if (")".equals(i)) {
                postfix.addAll(emptyStack(stack));
            } else if (stack.isEmpty() || stack.peek().equals("(")){ 
                stack.push(i);
            } else if (getPrecedence(i) > getPrecedence(stack.peek())) {
                stack.push(i);
            } else if (getPrecedence(i) == getPrecedence(stack.peek())) {
                if (getAssociativity(i) == Assoc.LEFT) {
                    postfix.add(stack.pop());
                }
                stack.push(i);
            } else {
                postfix.addAll(replaceTop(stack, i));
            } 
        }
        if (stack.contains("(") && !stack.contains(")")){
            throw new IllegalArgumentException(MISSING_OPERATOR);
        }

        while (!stack.isEmpty()){
            postfix.add(stack.pop());
        }
        return postfix;

    }

    List<String> replaceTop(Deque<String> stack, String i){
        List<String> postfix = new ArrayList<String>();
        while (!stack.isEmpty() || getPrecedence(i) < getPrecedence(stack.peek())){
            postfix.add(stack.pop());
            if (stack.isEmpty()){
                break;
            }
        }
        stack.push(i);
        return postfix;
    }

    List<String> emptyStack(Deque<String> stack){
        List<String> postfix = new ArrayList<String>();
        String popped;
        while (true) {
            if (stack.isEmpty()){
                throw new IllegalArgumentException(MISSING_OPERATOR);
            }
            popped = stack.pop();
            if ("(".equals(popped)){
                break;
            } else {
                postfix.add(popped);
            }
        }
        return postfix;
    }

    int getPrecedence(String op) {
        if ("+-".contains(op)) {
            return 2;
        } else if ("*/".contains(op)) {
            return 3;
        } else if ("^".contains(op)) {
            return 4;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    Assoc getAssociativity(String op) {
        if ("+-*/".contains(op)) {
            return Assoc.LEFT;
        } else if ("^".contains(op)) {
            return Assoc.RIGHT;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    enum Assoc {
        LEFT,
        RIGHT
    }

    // ---------- Tokenize -----------------------

    // List String (not char) because numbers (with many chars)
    List<String> tokenize(String expr) {
        List<String> tmp = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (char c : expr.toCharArray()){
            if (isOperator(String.valueOf(c))) {
                if (!sb.isEmpty()){
                    tmp.add(sb.toString());
                    sb.setLength(0);
                }
                tmp.add(String.valueOf(c));
            } else if (c==' ') {
                if (!sb.isEmpty()){
                    tmp.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        if (!sb.isEmpty()){
            tmp.add(sb.toString());
        }
        return tmp;
    }

    boolean isOperator(String c){
        return "()+-*/^)".contains(c);
    }
}
