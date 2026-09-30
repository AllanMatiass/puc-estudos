package com.converge.filapilha;

public class Arquiteto {
    public boolean comeu;
    public Banco criarBancoFudido(){
        return this.comeu ? new Banco(true) : new Banco(false);
    }

    public Arquiteto(boolean comeu){
        this.comeu = comeu;
    }
}
