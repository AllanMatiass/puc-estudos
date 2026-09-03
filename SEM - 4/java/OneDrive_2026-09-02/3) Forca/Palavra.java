import java.util.Scanner;

public class Palavra implements Comparable<Palavra>
{
    private String texto;

    public Palavra (String texto) throws Exception
    {
        // verifica se o texto recebido � nulo ou ent�o vazio,
        // ou seja, sem nenhum caractere, lan�ando exce��o.
        // armazena o texto recebido em this.texto.
        if (texto == null || texto.isBlank()) throw new Exception("texto vazio");
        this.texto = texto;
    }

    public int getQuantidade (char letra)
    {
        // percorre o String this.texto, conta e retorna
        // quantas letras existem nele iguais a letra fornecida
        short qtd = 0;
        for (char c : texto.toCharArray()){
            if (c == letra) qtd++;
        }

        return qtd;
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        // se i==0, retorna a posicao em que ocorre a primeira
        // aparicao de letra fornecida em this.texto;

        // se i==1, retorna a posicao em que ocorre a segunda
        // aparicao de letra fornecida em this.texto;
        // se i==2, retorna a posicao em que ocorre a terceira
        // aparicao de letra fornecida em this.texto;
        // e assim por diante.

        int length = texto.length();

        int aparicao = -1;
        for (int j = 0; j < length; j++){
            if (letra == texto.charAt(j)){
                if (++aparicao == i) return j;
            }
        }


        // lan�ar excecao caso nao encontre em this.texto
        // a I�zima apari��o da letra fornecida.
        throw new Exception("Essa letra nao aparece mais de " + aparicao + " vezes");
    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    @Override
    public String toString ()
    {
        return this.texto;
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte�do, retornando
        // true no caso afirmativo ou false no caso negativo

        return obj == this;
    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        return 0;
    }

    @Override
    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }
}
