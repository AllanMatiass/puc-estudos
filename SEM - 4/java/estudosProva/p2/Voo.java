public class Voo implements Cloneable {
    private int numeroVoo;
    private String nomePassageiro;
    private int qtdBagagens;

    public String getNomePassageiro(){
        return this.nomePassageiro;
    }
    public int getNumeroVoo(){
        return this.numeroVoo;
    }

    public int getQtdBagagens(){
        return this.qtdBagagens;
    }

    public void setNumeroVoo(int n){
        this.numeroVoo = n;
    }

    public void setNomePassageiro(String s){
        this.nomePassageiro = s;
    }

    public void setQtdBagagens(int q){
        this.qtdBagagens = q;
    }

    private Voo(Voo v) throws Exception {
        if (v == null) throw new Exception("Erro");
        this.numeroVoo = v.numeroVoo;
        this.nomePassageiro = v.nomePassageiro;
        this.qtdBagagens = v.qtdBagagens;
    }

    @Override
    public Voo clone() {
        Voo ret = null;

        try{
            ret = new Voo(this);
        } catch (Exception _){
        }

        return ret;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (!o.getClass().equals(this.getClass())) return false;

        if (this == o) return true;

        Voo voo = (Voo) o;

        return voo.qtdBagagens == this.qtdBagagens &&
                voo.nomePassageiro.equals(this.nomePassageiro) &&
                voo.numeroVoo == this.numeroVoo;
    }

    @Override
    public int hashCode(){
        int ret = 7;

        ret *= 7 + this.nomePassageiro.hashCode();
        ret *= 7 * Integer.valueOf(this.numeroVoo).hashCode();
        ret *= 7 * Integer.valueOf(this.qtdBagagens).hashCode();

        if (ret < 0) ret = -ret;
        return ret;
    }
}
