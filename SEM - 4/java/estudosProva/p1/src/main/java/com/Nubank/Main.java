package com.Nubank;

import com.Nubank.domain.Cliente;
import com.Nubank.domain.Expressao;
import com.Nubank.domain.Solicitacao;

import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        Solicitacao s = new Solicitacao();
        String[] ops = {
                "ADD 10", "ADD 20",
                "URGENT 30", "ADD 40",
                "URGENT 50"
        };

        for (String op : ops) {
            s.operate(op);
        }

        System.out.println("JOSEMOA");

        while (!s.pq.isEmpty()) {
            System.out.println(s.pq.poll());
        }
    }
}
