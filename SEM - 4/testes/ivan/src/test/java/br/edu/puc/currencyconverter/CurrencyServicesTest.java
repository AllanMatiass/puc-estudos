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

    // ==========================================
    // Testes Complementares de Domínio, Limites e Robustez
    // ==========================================

    @Test
    @DisplayName("Validação de Parâmetro Nulo: isValidCurrency com null lança NullPointerException")
    public void deveDemonstrarNullPointerExceptionEmIsValidCurrencyComNull() {
        // Act & Assert
        // O código chama allCurrency[i].getCodigoAlfabetico().compareToIgnoreCase(codigoAlfabetico) sem validar null
        assertThrows(NullPointerException.class, () -> {
            currencyServices.isValidCurrency(null);
        });
    }

    @Test
    @DisplayName("Validação de Parâmetro Nulo: getCurrency com null lança NullPointerException")
    public void deveDemonstrarNullPointerExceptionEmGetCurrencyComNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            currencyServices.getCurrency(null);
        });
    }

    @Test
    @DisplayName("Anomalia Funcional de Sensibilidade de Caixa: converter rejeita moedas em minúsculo aceitas em isValidCurrency")
    public void deveDemonstrarFalhaAoConverterComMoedasMinusculasDevidoSensibilidadeDeCaixaNaChave() {
        // Arrange
        String de = "brl";
        String para = "usd";
        Calendar hoje = Calendar.getInstance();
        double quantia = 100.0;

        // Act & Assert
        // isValidCurrency aceita minúsculo (case-insensitive):
        assertTrue(currencyServices.isValidCurrency(de));
        assertTrue(currencyServices.isValidCurrency(para));

        // Porém converter concatena a chave como "brl->usd", que não bate com "BRL->USD" no HashMap do mock:
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, hoje, quantia);
        });
        assertTrue(exception.getMessage().contains("Não há taxa de conversão"));
    }

    @Test
    @DisplayName("Anomalia Crítica: Data de Agosto consome indevidamente a cotação de Julho")
    public void deveDemonstrarAnomaliaDataDeAgostoConsumindoTaxaDeJulho() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar dataAgosto = Calendar.getInstance();
        dataAgosto.set(Calendar.YEAR, 2014);
        dataAgosto.set(Calendar.MONTH, Calendar.AUGUST); // Calendar.AUGUST é 7
        dataAgosto.set(Calendar.DAY_OF_MONTH, 15);
        double quantia = 100.0;

        // Act
        // Como o código faz month = dt.get(Calendar.MONTH) sem somar 1, para agosto (7)
        // a chave gerada é "15/07/2014". O mock possui taxa para 15/07/2014 (0.45),
        // portanto a conversão para agosto tem sucesso utilizando a taxa de julho!
        double resultado = currencyServices.converter(de, para, dataAgosto, quantia);

        // Assert
        assertEquals(45.0, resultado, 0.0001,
                "Demonstra anomalia: consulta para 15/08/2014 utilizou a cotação de 15/07/2014 (0.45)!");
    }

    @Test
    @DisplayName("Todas as Cotações Históricas de 15/07/2014 cadastradas no Mock")
    public void deveConverterTodasAsCotacoesHistoricasCadastradas() {
        // Arrange
        Calendar dataHistorica = Calendar.getInstance();
        dataHistorica.set(Calendar.YEAR, 2014);
        dataHistorica.set(Calendar.MONTH, 7); // Mês 7 para gerar "07" no getter do código original
        dataHistorica.set(Calendar.DAY_OF_MONTH, 15);

        // Act & Assert
        // BRL -> USD: 0.45 * 100 = 45.0
        assertEquals(45.0, currencyServices.converter("BRL", "USD", dataHistorica, 100.0), 0.0001);
        // BRL -> GBP: 0.26 * 100 = 26.0
        assertEquals(26.0, currencyServices.converter("BRL", "GBP", dataHistorica, 100.0), 0.0001);
        // BRL -> EUR: 0.33 * 100 = 33.0
        assertEquals(33.0, currencyServices.converter("BRL", "EUR", dataHistorica, 100.0), 0.0001);
    }

    @Test
    @DisplayName("Todas as 10 Cotações Atuais cadastradas no Mock (Ida e Volta)")
    public void deveConverterTodosOs10ParesComCotacaoAtual() {
        // Arrange
        Calendar hoje = Calendar.getInstance();

        // Act & Assert - 5 taxas de BRL para outras moedas
        assertEquals(20.0, currencyServices.converter("BRL", "USD", hoje, 100.0), 0.0001); // 0.20
        assertEquals(14.0, currencyServices.converter("BRL", "GBP", hoje, 100.0), 0.0001); // 0.14
        assertEquals(17.0, currencyServices.converter("BRL", "EUR", hoje, 100.0), 0.0001); // 0.17
        assertEquals(16.0, currencyServices.converter("BRL", "CHF", hoje, 100.0), 0.0001); // 0.16
        assertEquals(27.0, currencyServices.converter("BRL", "CAD", hoje, 100.0), 0.0001); // 0.27

        // Act & Assert - 5 taxas das outras moedas para BRL
        assertEquals(511.0, currencyServices.converter("USD", "BRL", hoje, 100.0), 0.0001); // 5.11
        assertEquals(586.0, currencyServices.converter("EUR", "BRL", hoje, 100.0), 0.0001); // 5.86
        assertEquals(683.0, currencyServices.converter("GBP", "BRL", hoje, 100.0), 0.0001); // 6.83
        assertEquals(622.0, currencyServices.converter("CHF", "BRL", hoje, 100.0), 0.0001); // 6.22
        assertEquals(364.0, currencyServices.converter("CAD", "BRL", hoje, 100.0), 0.0001); // 3.64
    }

    @Test
    @DisplayName("Valores Limites de Data Histórica: 14/07/2014 e 16/07/2014 não possuem taxa")
    public void deveLancarExcecaoParaDiasImediatamenteAnteriorEPosteriorADataHistorica() {
        // Arrange
        Calendar dia14 = Calendar.getInstance();
        dia14.set(2014, 7, 14);

        Calendar dia16 = Calendar.getInstance();
        dia16.set(2014, 7, 16);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", dia14, 100.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", dia16, 100.0);
        });
    }

    @Test
    @DisplayName("Valores Limites de Data: Ano bissexto (29/02/2024) e ano comum (28/02/2023) sem taxa")
    public void deveLancarExcecaoParaDatasValidasDeFevereiroSemTaxa() {
        // Arrange
        Calendar bissexto = Calendar.getInstance();
        bissexto.set(2024, 2, 29); // Mês no código sem +1

        Calendar comum = Calendar.getInstance();
        comum.set(2023, 2, 28);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", bissexto, 100.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter("BRL", "USD", comum, 100.0);
        });
    }

    @Test
    @DisplayName("Anomalia de Robustez: API aceita Double.NaN e Double.POSITIVE_INFINITY sem validar")
    public void deveDemonstrarAceitacaoDeValoresNaoFinitosNaNEInfinito() {
        // Arrange
        Calendar hoje = Calendar.getInstance();

        // Act & Assert
        // A condição 'if (quantia < 0)' avalia para false quando quantia é NaN ou +Infinity!
        // Logo, a API aceita esses valores e calcula o produto:
        double resultadoNaN = currencyServices.converter("BRL", "USD", hoje, Double.NaN);
        assertTrue(Double.isNaN(resultadoNaN), "API permite quantia NaN e retorna NaN");

        double resultadoInf = currencyServices.converter("BRL", "USD", hoje, Double.POSITIVE_INFINITY);
        assertTrue(Double.isInfinite(resultadoInf), "API permite quantia Infinity e retorna Infinity");
    }

    @Test
    @DisplayName("Anomalia de Serialização: getAllCurrencies retorna JSON com aspas duplamente escapadas")
    public void deveDemonstrarDuplaSerializacaoJsonEmGetAllCurrencies() {
        // Act
        String jsonRetornado = currencyServices.getAllCurrencies();

        // Assert
        // Como o método faz gson.toJson(moedas) seguido de gson.toJson(json),
        // o retorno começa e termina com aspas de string JSON encapsulada:
        assertTrue(jsonRetornado.startsWith("\"") && jsonRetornado.endsWith("\""));
        assertTrue(jsonRetornado.contains("\\\"codigoAlfabetico\\\""));
    }
}
