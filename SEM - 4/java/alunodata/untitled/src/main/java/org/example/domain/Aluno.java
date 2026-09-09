package org.example.domain;

public class Aluno implements Cloneable
{
    private int ra;
    private byte   idade;
    private String nome;
    private Data   nascimento;

    public Aluno (int ra, byte i, String no, Data na) throws Exception {

        this.setIdade      (i);
        this.setNome       (no);
        this.setNascimento (na);
    }

    public void setIdade (byte i) throws Exception
    {
        if (i< 0) throw new Exception ("Idade invalida");

        this.idade = i;
    }

    public byte getIdade ()
    {
        return this.idade;
    }

    public void setNome (String no) throws Exception
    {
        if (no==null || no.isEmpty()) throw new Exception("Nome ausente");

        this.nome = no;
    }

    public String getNome ()
    {
        return this.nome;
    }

    public void setNascimento (Data na) throws Exception
    {
        if (na==null) throw new Exception ("Data ausente");

        this.nascimento = na.clone();
    }

    public Data getNascimento ()
    {
        return this.nascimento;
    }

    public int getRa(){
        return this.ra;
    }

    public void setRa(int ra){
        this.ra = ra;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj == null) return false;

        if (obj instanceof Aluno a){
            return this.ra == a.ra;
        }

        return false;
    }


    @Override
    public int hashCode(){
        int ret = 2;

        ret = 7 * ret + Integer.valueOf(this.ra).hashCode();
        ret = 7 * ret + Integer.valueOf(this.idade).hashCode();
        ret = 7 * ret + Byte.valueOf(this.idade).hashCode();
        ret = 7 * ret + this.nascimento.hashCode();
        ret = 7 * ret + this.nome.hashCode();

        if (ret < 0) ret = -ret;

        return ret;
    }

    @Override
    public String toString(){
        return String.valueOf(this.ra);
    }

    @Override
    public Aluno clone() {
        try {

            Aluno clone = (Aluno) super.clone();
            clone.nome = this.nome;
            clone.nascimento = this.nascimento;
            clone.idade = this.idade;
            clone.ra = this.ra;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
