package br.edu.puc.currencyconverter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

//modificador de acesso publico, por ser ENUM genérica ao sistema
public enum MoedaISO4217 {
    // Definição das principais moedas mundiais segundo a ISO 4217
    BRL("BRL", 986, 2, "Real Brasileiro", new Locale("pt", "BR")),
    USD("USD", 840, 2, "Dólar Americano", Locale.US),
    EUR("EUR", 978, 2, "Euro", Locale.FRANCE),
    GBP("GBP", 826, 2, "Libra Esterlina", Locale.UK),
    JPY("JPY", 392, 0, "Iene Japonês", Locale.JAPAN),
    CHF("CHF", 756, 2, "Franco Suíço", new Locale("de", "CH")),
    CAD("CAD", 124, 2, "Dólar Canadense", Locale.CANADA),
    AUD("AUD", 036, 2, "Dólar Australiano", new Locale("en", "AU")),
    CNY("CNY", 156, 2, "Yuan Chinês", Locale.CHINA);

    private final String codigoAlfabetico;
    private final int codigoNumerico;
    private final int casasDecimais;
    private final String descricao;
    private final Locale locale;

    // Construtor do Enum
    MoedaISO4217(String codigoAlfabetico, int codigoNumerico, int casasDecimais, String descricao, Locale locale) {
        this.codigoAlfabetico = codigoAlfabetico;
        this.codigoNumerico = codigoNumerico;
        this.casasDecimais = casasDecimais;
        this.descricao = descricao;
        this.locale = locale;
    }

    // Getters para acessar as propriedades ISO
    public String getCodigoAlfabetico() { return codigoAlfabetico; }
    public int getCodigoNumerico() { return codigoNumerico; }
    public int getCasasDecimais() { return casasDecimais; }
    public String getDescricao() { return descricao; }
    public Locale getLocale() { return locale; }

    /**
     * Formata um valor monetário de acordo com os padrões locais da moeda.
     * Utiliza BigDecimal para evitar problemas de arredondamento de ponto flutuante.
     */
    public String formatar(BigDecimal valor) {
        if (valor == null) return "";
        NumberFormat formatador = NumberFormat.getCurrencyInstance(this.locale);
        formatador.setMinimumFractionDigits(this.casasDecimais);
        formatador.setMaximumFractionDigits(this.casasDecimais);
        return formatador.format(valor);
    }
    
    /**
     * Busca uma moeda pelo seu código alfabético de 3 letras (Ex: "USD").
     */
    public static MoedaISO4217 obterPorCodigo(String codigo) {
        for (MoedaISO4217 moedasDaEnum : values()) {
            if (moedasDaEnum.getCodigoAlfabetico().equalsIgnoreCase(codigo)) {
                return moedasDaEnum; //retorna MoedaAtual a Enum
            }
        }
        throw new IllegalArgumentException("Moeda com o código ISO " + codigo + " não suportada.");
    }
    
    
}