public class ControladorDeLetrasJaDigitadas implements Cloneable
{
    private String letrasJaDigitadas = "";

    public ControladorDeLetrasJaDigitadas ()
    {

    }

    public boolean isJaDigitada (char letra)
    {
        // percorrer o String this.letrasJaDigitadas e verificar se ele
        // possui a letra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo
        for (int i = 0; i < letrasJaDigitadas.length(); i++){
            char letraAtual = letrasJaDigitadas.toCharArray()[i];
            if (letraAtual == letra) return true;
        }

        return false;
    }

    public void registre (char letra) throws Exception
    {
        // verifica se a letra fornecida ja foi digitada (pode usar
        // o m�todo this.isJaDigitada, para isso), lancando uma exce��o

        if (this.isJaDigitada(letra)) throw new Exception("Ja digitada");
        // em caso afirmativo.
        // concatena a letra fornecida a this.letrasJaDigitadas.

        this.letrasJaDigitadas += letra;
    }

    @Override
    public String toString ()
    {
        // retorna um String com TODAS as letras presentes em
        // this.letrasJaDigitadas separadas por v�rgula (,).
        return letrasJaDigitadas.replace("", ",");
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj s�o iguais
        return this == obj;
    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        return 0;
    }

    private ControladorDeLetrasJaDigitadas(
    ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)// construtor de c�pia
    {
        // copiar c.letrasJaDigitadas em this.letrasJaDigitadas
        this.letrasJaDigitadas = controladorDeLetrasJaDigitadas.letrasJaDigitadas;
    }

    @Override
    public Object clone ()
    {
        // criar uma c�pia do this com o construtor de c�pia e retornar
        return new ControladorDeLetrasJaDigitadas(this);
    }
}
