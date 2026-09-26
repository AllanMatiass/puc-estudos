package com.Nubank.domain;


import java.util.List;
import java.util.Stack;

public class Expressao {

    private final List<Character> open = List.of('(', '[', '{');
    private final List<Character> close = List.of(')', ']', '}');

    private final Stack<Character> s = new Stack<>();

    public boolean isValid(String exp) {
        if (exp.length() < 3) {
            return false;
        }

        for (char ch : exp.toCharArray()) {

            if (Character.isSpaceChar(ch) || Character.isDigit(ch)) {
                continue;
            }

            if (open.contains(ch)) {
                s.push(ch);
            } else if (close.contains(ch)) {
                if (s.isEmpty()) return false;

                char top = s.pop();

                if ((ch == ')' && top != '(') ||
                        (ch == ']' && top != '[') ||
                        (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }

        return s.isEmpty();
    }
}
