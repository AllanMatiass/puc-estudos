public class Tracinhos implements Cloneable
{
    private char[] texto;

    public Tracinhos (int qtd) throws Exception
    {
        // verifica se qtd n�o � positiva, lan�ando uma exce��o.
        if (qtd < 0) throw new Exception("qtd deve ser positivo");
        // instancia this.texto com um vetor com tamanho igual qtd.
        texto = new char[qtd];
        // preenche this.texto com underlines (_).

        for (int i = 0; i < qtd; i++){
            this.texto[i] = '_';
        }

    }

    public void revele (int posicao, char letra) throws Exception
    {
        // verifica se posicao � negativa ou ent�o igual ou maior
        // do que this.texto.length, lan�ando uma exce��o.
        if (posicao < 0 || posicao >= this.texto.length) throw new Exception("fora do range do texto, nao da pra revelar");

        // verifica se em this.texto[posicao] ainda tem '_'; nao
        // tendo, lan�a exce��o.

        if (this.texto[posicao] != '_') throw new Exception("Não Tem underline nessa posicao");

        // armazena a letra fornecida na posicao tambem fornecida
        // do vetor this.texto

        this.texto[posicao] = letra;
    }

    public boolean isAindaComTracinhos ()
    {
        // percorre o vetor de char this.texto e verifica
        // se o mesmo ainda contem algum underline ou se ja
        // foram todos substituidos por letras; retornar true
        // caso ainda reste algum underline, ou false caso
        // contrario

        for (char c : this.texto){
            if (c == '_') return true;
        }

        return false;
    }

    @Override
    public String toString ()
    {
        // retorna um String com TODOS os caracteres que h�
        // no vetor this.texto, intercalados com espa�os em
        // branco
        return new String(this.texto).replace("", " ");
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte�do, retornando
        // true no caso afirmativo ou false no caso negativo

        if (obj == null) return false;
        if (obj == this) return true;

        if (obj instanceof Tracinhos t){
            return t.toString().equals(this.toString());
        }

        return false;
    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        return 0;
    }

    private Tracinhos (Tracinhos t) // construtor de c�pia
    {
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copilar o conte�do de t.texto para this.texto
        // this.texto = new char[t.texto.length];
        this.texto = t.texto;

    }

    @Override
    public Object clone ()
    {
        // retornar uma copia de this
        return new Tracinhos(this);
    }
}
