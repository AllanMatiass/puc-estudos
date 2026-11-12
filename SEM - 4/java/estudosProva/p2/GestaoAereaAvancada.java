import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GestaoAereaAvancada extends GestaoAereaBase {

    public GestaoAereaAvancada(int tamVetor, double valorBagagemBase) {
        super(tamVetor, valorBagagemBase);
    }

    @Override
    public void registrarVoo(Voo v) {

    }

    @Override
    public double calcularFaturamentoPrevisto() {
        return 0;
    }

    @Override
    public void cancelarVoo(String nomePassageiro) {

        for (int i = 0; i < this.quantidadeVoos; i++){
            Voo v = this.voos[i];
            if (v == null && i == this.quantidadeVoos - 1){
                this.quantidadeVoos--;
                return;
            }

            if (v == null){
                redimensionar(i);
                this.quantidadeVoos--;
                continue;
            }

            if (v.getNomePassageiro().equalsIgnoreCase(nomePassageiro)){
                this.voos[i] = null;
                this.quantidadeVoos--;
                redimensionar(i);
            }
        }

        int corte = this.voos.length / 2 / 2;
        if (this.quantidadeVoos <= corte &&
                this.capacidadeInicial > this.voos.length / 2){

            Voo[] novos = new Voo[this.voos.length / 2];
            for (int i = 0; i < novos.length; i++){
                novos[i] = this.voos[i];
            }
            this.voos = novos;
        }
    }

    public int aplicarDescontoFrequente(){
        Map<String, int[]> seen = new HashMap<>();
        int ret = 0;

        for (Voo v : this.voos){
            String nome = v.getNomePassageiro();
            int num = v.getNumeroVoo();

            int[] voosPassageiro = seen.get(nome);

            if (voosPassageiro == null){
                int[] novo = new int[this.quantidadeVoos];
                novo[0] = num;
                seen.put(nome, novo);
                continue;
            }

            for (int i = 0 ; i < voosPassageiro.length; i++){

                int n = voosPassageiro[i];

                if (n == 0){
                    voosPassageiro[i] = num;
                    break;
                }
            }

            for (int n : voosPassageiro){
                if (n == num){
                    v.setQtdBagagens((int)(v.getQtdBagagens() * 0.85));
                    ret++;
                }
            }

        }
        return ret;
    }

    private void redimensionar(int idx) {
        for (int j = idx; j < quantidadeVoos; j++) {
            voos[j] = voos[j + 1];
        }

        voos[quantidadeVoos - 1] = null;
    }
}
