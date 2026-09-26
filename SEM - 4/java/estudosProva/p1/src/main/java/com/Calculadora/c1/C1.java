package com.Calculadora.c1;

import java.util.Scanner;

public class C1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean run = true;

        while (run) {
            System.out.println("Digite o primeiro numero: ");
            int  a = sc.nextInt();
            sc.nextLine();

            System.out.println("Digite o segundo numero: ");
            int b = sc.nextInt();
            sc.nextLine();

            System.out.println("Digite a operacao: ");
            String cmd = sc.nextLine();


            switch (cmd) {
                case "+":
                    System.out.println(a + b);
                    break;
                case "-":
                    System.out.println(a - b);
                    break;
                case "*":
                    System.out.println(a * b);
                    break;
                case "/":
                    if (b == 0) throw new IllegalArgumentException("B não pode ser 0");
                    System.out.println(a / b);
                    break;
            }

            System.out.println("Quer continuar? (true/false): ");

            run = sc.nextBoolean();
        }
    }
}
