package com.Nubank.domain;

public class Cliente implements Comparable<Cliente> {
    public String nome;
    public int prioridade;

    private static int COUNTER = 1;
    public Cliente (){
        this.nome = "dummy" + Cliente.COUNTER++;
        this.prioridade = (int) (Math.random() * 3) + 1;
    }

    @Override
    public int compareTo(Cliente o) {
        return o.prioridade - this.prioridade;
    }
}
