public interface PadraoGestaoFrequente {
    void registrarVoo(Voo v);
    double calcularFaturamentoPrevisto();
    void cancelarVoo(String nomePassageiro);
}
