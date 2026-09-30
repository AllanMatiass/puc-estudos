package br.edu.puc.currencyconverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Domínio e Serviço: CurrencyServices")
public class CurrencyServicesTest {

    private CurrencyServices currencyServices;

    @BeforeEach
    public void setUp() {
        // Arrange comum para os testes de serviço
        currencyServices = new CurrencyServices();
    }

    // ==========================================
    // Testes de getAllCurrencies
    // ==========================================

    @Test
    @DisplayName("Deve retornar JSON contendo a lista de todas as moedas disponíveis")
    public void deveRetornarTodasAsMoedasEmFormatoJson() {
        // Arrange (preparado no setUp)

        // Act
        String json = currencyServices.getAllCurrencies();

        // Assert
        assertNotNull(json);
        assertFalse(json.isEmpty());
        assertTrue(json.contains("BRL"));
        assertTrue(json.contains("USD"));
        assertTrue(json.contains("EUR"));
    }

    // ==========================================
    // Testes de isValidCurrency
    // ==========================================

    @Test
    @DisplayName("Deve retornar true para código de moeda existente (maiúsculo)")
    public void deveRetornarTrueParaMoedaValidaMaiuscula() {
        // Arrange
        String codigo = "BRL";

        // Act
        boolean ehValida = currencyServices.isValidCurrency(codigo);

        // Assert
        assertTrue(ehValida);
    }

    @Test
    @DisplayName("Deve retornar true para código de moeda existente em minúsculo (case insensitive)")
    public void deveRetornarTrueParaMoedaValidaMinuscula() {
        // Arrange
        String codigo = "usd";

        // Act
        boolean ehValida = currencyServices.isValidCurrency(codigo);

        // Assert
        assertTrue(ehValida);
    }

    @Test
    @DisplayName("Deve retornar false para código de moeda inexistente")
    public void deveRetornarFalseParaMoedaInexistente() {
        // Arrange
        String codigoInexistente = "XYZ";

        // Act
        boolean ehValida = currencyServices.isValidCurrency(codigoInexistente);

        // Assert
        assertFalse(ehValida);
    }

    // ==========================================
    // Testes de getCurrency
    // ==========================================

    @Test
    @DisplayName("Deve obter MoedaISO4217 para código existente")
    public void deveRetornarMoedaParaCodigoValido() {
        // Arrange
        String codigo = "EUR";

        // Act
        MoedaISO4217 moeda = currencyServices.getCurrency(codigo);

        // Assert
        assertNotNull(moeda);
        assertEquals(MoedaISO4217.EUR, moeda);
    }

    @Test
    @DisplayName("Deve retornar null para código de moeda não cadastrado")
    public void deveRetornarNullParaCodigoInexistente() {
        // Arrange
        String codigoInexistente = "ABC";

        // Act
        MoedaISO4217 moeda = currencyServices.getCurrency(codigoInexistente);

        // Assert
        assertNull(moeda);
    }

    // ==========================================
    // Testes de converter: Partições da Data de Cotação e Valores
    // ==========================================

    @Test
    @DisplayName("Partição Válida (Data Atual): Deve converter com sucesso na data de hoje")
    public void deveConverterNaDataAtualComSucesso() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar hoje = Calendar.getInstance();
        double quantia = 100.0;
        double valorEsperado = 20.0; // Taxa de BRL->USD hoje é 0.20

        // Act
        double resultado = currencyServices.converter(de, para, hoje, quantia);

        // Assert
        assertEquals(valorEsperado, resultado, 0.0001);
    }

    @Test
    @DisplayName("Partição Válida (Data Atual - Inversa): Deve converter USD para BRL na data de hoje")
    public void deveConverterUsdParaBrlNaDataAtualComSucesso() {
        // Arrange
        String de = "USD";
        String para = "BRL";
        Calendar hoje = Calendar.getInstance();
        double quantia = 10.0;
        double valorEsperado = 51.10; // Taxa USD->BRL hoje é 5.11

        // Act
        double resultado = currencyServices.converter(de, para, hoje, quantia);

        // Assert
        assertEquals(valorEsperado, resultado, 0.0001);
    }

    @Test
    @DisplayName("Partição Válida (Data Histórica): Deve converter com sucesso para data histórica cadastrada")
    public void deveConverterComDataHistoricaCadastrada() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar dataHistorica = Calendar.getInstance();
        // O código formata dt.get(Calendar.MONTH) sem somar 1.
        // Logo, para gerar "15/07/2014", o mês passado ao Calendar precisa resultar em 7 no getter:
        dataHistorica.set(Calendar.YEAR, 2014);
        dataHistorica.set(Calendar.MONTH, 7);
        dataHistorica.set(Calendar.DAY_OF_MONTH, 15);
        double quantia = 100.0;
        double valorEsperado = 45.0; // Taxa histórica BRL->USD (15/07/2014) é 0.45

        // Act
        double resultado = currencyServices.converter(de, para, dataHistorica, quantia);

        // Assert
        assertEquals(valorEsperado, resultado, 0.0001);
    }

    @Test
    @DisplayName("Deve detectar falha ao converter uma data de julho devido ao tratamento incorreto do índice do mês")
    public void deveDemonstrarFalhaAoUsarJulhoDevidoIndiceZero() {

        System.out.println(
                "\nAVISO: Este teste demonstra uma anomalia conhecida " +
                        "no tratamento do mês de julho: " + "CurrencyServicesTest.deveDemonstrarFalhaAoUsarJulhoDevidoIndiceZero()"
        );
        // Arrange
        String de = "BRL";
        String para = "USD";

        Calendar dataJulho = Calendar.getInstance();
        dataJulho.set(Calendar.YEAR, 2014);
        dataJulho.set(Calendar.MONTH, Calendar.JULY); // JULY = 6 (índice base-0)
        dataJulho.set(Calendar.DAY_OF_MONTH, 15);

        double quantia = 100.0;

        // Act & Assert
        // Calendar.JULY possui valor 6, porém representa o mês de julho.
        // Caso a implementação trate esse valor como um mês convencional (1-12),
        // a data poderá ser formatada incorretamente como 15/06/2014.
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> currencyServices.converter(de, para, dataJulho, quantia)
        );

        assertTrue(exception.getMessage().contains("taxa de convers"));
    }

    @Test
    @DisplayName("Partição Inválida (Data Histórica Sem Cotação): Deve lançar exceção quando não houver cotação para a data")
    public void deveLancarExcecaoQuandoNaoHouverCotacaoParaDataInformada() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar dataSemCotacao = Calendar.getInstance();
        dataSemCotacao.set(Calendar.YEAR, 2020);
        dataSemCotacao.set(Calendar.MONTH, 0); // Janeiro
        dataSemCotacao.set(Calendar.DAY_OF_MONTH, 10);
        double quantia = 100.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, dataSemCotacao, quantia);
        });
        assertTrue(exception.getMessage().contains("taxa de convers"));
    }

    @Test
    @DisplayName("Partição Inválida (Data Futura): Deve lançar exceção ao consultar cotação para data futura sem taxa")
    public void deveLancarExcecaoParaDataFutura() {
        // Arrange
        String de = "BRL";
        String para = "EUR";
        Calendar dataFutura = Calendar.getInstance();
        dataFutura.add(Calendar.YEAR, 2);
        double quantia = 50.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, dataFutura, quantia);
        });
    }

    @Test
    @DisplayName("Valor Limite de Data: Ontem (D-1) sem cotação deve lançar exceção")
    public void deveLancarExcecaoParaOntemSemCotacao() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_YEAR, -1);
        double quantia = 100.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, ontem, quantia);
        });
    }

    @Test
    @DisplayName("Valor Limite de Data: Amanhã (D+1) sem cotação deve lançar exceção")
    public void deveLancarExcecaoParaAmanhaSemCotacao() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar amanha = Calendar.getInstance();
        amanha.add(Calendar.DAY_OF_YEAR, 1);
        double quantia = 100.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, amanha, quantia);
        });
    }

    @Test
    @DisplayName("Cobertura de Formatação de Data: Formatação de dia e mês com 1 e 2 dígitos")
    public void deveCobrirFormatacaoDeDataComDiaEMesDeUmEDoisDigitos() {
        // Arrange - Testa ramos com dia < 10 e mês < 10
        Calendar dataDigitoUnico = Calendar.getInstance();
        dataDigitoUnico.set(2021, 4, 5); // 05/04/2021

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", dataDigitoUnico, 10.0);
        });

        // Arrange - Testa ramos com dia >= 10 e mês >= 10
        Calendar dataDoisDigitos = Calendar.getInstance();
        dataDoisDigitos.set(2021, 11, 25); // 25/11/2021

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", dataDoisDigitos, 10.0);
        });
    }

    // ==========================================
    // Testes de Regras de Negócio e Robustez (Quantia, Moedas)
    // ==========================================

    @Test
    @DisplayName("Partição Inválida (Quantia Negativa): Deve lançar IllegalArgumentException para quantia < 0")
    public void deveLancarExcecaoParaQuantiaNegativa() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar hoje = Calendar.getInstance();
        double quantiaNegativa = -10.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, hoje, quantiaNegativa);
        });
        assertTrue(exception.getMessage().contains("Quantia informada"));
    }

    @Test
    @DisplayName("Valor Limite (Quantia Zero): Deve retornar 0.0 quando a quantia for 0")
    public void deveRetornarZeroParaQuantiaZero() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar hoje = Calendar.getInstance();
        double quantiaZero = 0.0;

        // Act
        double resultado = currencyServices.converter(de, para, hoje, quantiaZero);

        // Assert
        assertEquals(0.0, resultado, 0.0001);
    }

    @Test
    @DisplayName("Partição Inválida (Mesma Moeda): Deve lançar exceção ao converter entre moedas idênticas")
    public void deveLancarExcecaoParaMesmaMoedaDeOrigemEDestino() {
        // Arrange
        String de = "USD";
        String para = "USD";
        Calendar hoje = Calendar.getInstance();
        double quantia = 50.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, hoje, quantia);
        });
    }

    @Test
    @DisplayName("Partição Inválida (Par de Moedas Sem Cotação): Deve lançar exceção quando não há taxa cadastrada")
    public void deveLancarExcecaoParaParDeMoedasSemTaxaCadastrada() {
        // Arrange
        String de = "CAD";
        String para = "CHF";
        Calendar hoje = Calendar.getInstance();
        double quantia = 50.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, hoje, quantia);
        });
        assertTrue(exception.getMessage().contains("taxa de convers"));
    }

    @Test
    @DisplayName("Falha no código dado: Moeda inválida lança NullPointerException em vez de IllegalArgumentException")
    public void deveDemonstrarNullPointerExceptionParaMoedaInexistenteNoConverter() {
        // Arrange
        String deInvalida = "XYZ";
        String paraValida = "USD";
        Calendar hoje = Calendar.getInstance();
        double quantia = 100.0;

        // Act & Assert
        // Devido ao código não tratar o retorno null de getCurrency(de),
        // ele tenta chamar TaxaConversao(null, ...) e estoura NullPointerException.
        assertThrows(NullPointerException.class, () -> {
            currencyServices.converter(deInvalida, paraValida, hoje, quantia);
        });
    }

    @Test
    @DisplayName("Falha no código dado: Data nula lança NullPointerException sem validação defensiva")
    public void deveDemonstrarNullPointerExceptionParaDataNula() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar dataNula = null;
        double quantia = 100.0;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            currencyServices.converter(de, para, dataNula, quantia);
        });
    }
}
