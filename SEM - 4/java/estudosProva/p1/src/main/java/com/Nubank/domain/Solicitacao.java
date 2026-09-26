package com.Nubank.domain;

import java.util.Optional;
import java.util.PriorityQueue;

public class Solicitacao {

    public PriorityQueue<Command> pq = new PriorityQueue<>();

    public void operate(String op){
        String[]ops = op.split(" ");
        String cmd =  ops[0];
        Optional<Integer> value;
        try{
            value = Optional.of(Integer.parseInt(ops[1]));
        } catch (Exception e){
            value = Optional.of(-1);
            System.out.println("Sem valor");
        }

        Command c = new Command(value, cmd);

        switch (cmd){
            case "ADD":
            case "URGENT":
                this.add(c);
                break;
            case "PROCESS":
                this.process();
                break;
        }

    }

    private boolean add(Command c){
        return pq.add(c);
    }

    private boolean process(){
        if (pq.isEmpty()) return false;
        Command c = pq.poll();

        assert c != null;
        System.out.println("Processando comando - " + c.value);
        return true;
    }
}
