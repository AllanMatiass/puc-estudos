package com.Nubank;

import com.Nubank.domain.Cliente;

import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        PriorityQueue<Cliente> pqc = new PriorityQueue<>();


        for (int i = 0; i < 5; i++) {
            Cliente c2 = new Cliente();
            pqc.add(c2);
            System.out.println(c2.nome);
            System.out.println(c2.prioridade);
        }

        Cliente c = pqc.poll();
        System.out.printf("nome: %s\nprioridade: %d", c.nome, c.prioridade);
    }
}
