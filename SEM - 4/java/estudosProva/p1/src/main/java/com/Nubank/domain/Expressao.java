package com.Nubank.domain;

import java.util.Stack;

public class Expressao {

    public Stack<Character> brackets = new Stack<>();


    public boolean isValid(String exp){
        for (int i = 0; i < exp.length(); i++) {
            if (!String.valueOf(exp.charAt(i)).matches("[A-Za-z0-9(){}\\[\\]]")) {
                throw new IllegalArgumentException("Expressão contém caracteres inválidos");
            }

            if (!String.valueOf(exp.charAt(i)).matches("^[A-Za-z0-9]+$")){

            }
        }
    }
}
