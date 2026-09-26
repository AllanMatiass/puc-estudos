package com.Calculadora.c2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class C2 {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double sub(double a, double b) {
        return a - b;
    }

    public static double mul(double a, double b) {
        return a * b;
    }

    public static double div(double a, double b) {
        if (b == 0) throw new IllegalArgumentException("B Não pode ser 0");
        return a / b;
    }

    public static double getResultado(char op, double a, double b){
        Map<Character, Double> map = Map.of(
                '+', add(a,b),
                '-', sub(a,b),
                '*', mul(a,b),
                '/', div(a,b)
        );

        return map.getOrDefault(op, 0.0);
    }

    public static void menu(){

        boolean run = true;

        while (run){

            Scanner sc = new Scanner(System.in);

            double a = sc.nextDouble();
            sc.nextLine();
            double b = sc.nextDouble();
            sc.nextLine();

            char op = sc.nextLine().charAt(0);

            System.out.println(
                    getResultado(op, a, b)
            );

            System.out.println("Quer continuar? (true/false): ");
            run = sc.nextBoolean();
        }

    }

    public static void main(String[] args) {
        menu();
    }

}
