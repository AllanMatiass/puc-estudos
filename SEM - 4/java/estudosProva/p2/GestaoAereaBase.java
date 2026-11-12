public abstract class GestaoAereaBase implements PadraoGestaoFrequente {
    protected Voo[] voos;
    protected double valorBagagemBase;
    protected int quantidadeVoos = 0;
    protected int capacidadeInicial = 0;

    public GestaoAereaBase(int tamVetor, double valorBagagemBase){
        this.voos = new Voo[tamVetor];
        this.valorBagagemBase = valorBagagemBase;
    }
}
