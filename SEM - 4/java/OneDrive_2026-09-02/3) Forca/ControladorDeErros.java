public class ControladorDeErros implements Cloneable
{
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
        // verifica se qtdMax fornecida n�o � positiva, lan�ando
        // uma exceção.
        // armazena qtdMax fornecida em this.qtdMax.

        if (qtdMax < 0) throw new Exception("qtdMax Não pode ser menor q 0");
        this.qtdMax = qtdMax;
    }

    public void registreUmErro () throws Exception
    {
        // verifica se this.qtdErr ja � igual a this.qtdMax,
        // lan�ando excecao em caso positivo ou
        // incrementando this.qtdErr em caso negativo
        if (this.qtdErr >= this.qtdMax) {
            throw new Exception("Pode não man, ja errou demais");
        }

        this.qtdErr++;
    }

    public boolean isAtingidoMaximoDeErros  ()
    {
        // returna true se this.qtdErr for igual a this.qtdMax,
        // ou ent�o false, caso contrario.
        return this.qtdErr == this.qtdMax;
    }

    @Override
    public String toString ()
    {
        return this.qtdErr + " de " + this.qtdMax;
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte�do, retornando
        // true no caso afirmativo ou false no caso negativo

        if (obj == null) return false;
        if (this == obj) return true;

        if (obj instanceof ControladorDeErros ref){
            return ref.qtdErr == this.qtdErr || ref.qtdMax == this.qtdMax;
        }

        return false;
    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        return 0;
    }

    private ControladorDeErros (ControladorDeErros c) // construtor de c�pia
    {
        this.qtdErr = c.qtdErr;
        this.qtdMax = c.qtdMax;
    }

    @Override
    public Object clone ()
    {
        return new ControladorDeErros(this);
    }
}
