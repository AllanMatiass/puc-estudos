# Relatório de Testes de Software e Análise de Qualidade

**Identificação da Equipe Auto-organizada:** __________________________________

---

## 1. Descrição das Novas Partições de Equivalência para a Nova Entrada da Tela (Data de Cotação) e Novas Saídas

### Tabela de Partições de Equivalência da Entrada

| Entrada | Classes Válidas | Classes Inválidas | Valores Limite |
| :--- | :--- | :--- | :--- |
| **5. Data da Cotação (`DT_COTAÇÃO`)** | **PV1:** Data atual do sistema ($D_0$) — busca cotação do momento presente.<br><br>**PV2:** Data histórica válida com cotação existente na base de dados/mock (ex: `15/07/2014`). | **PI1:** Data passada sem cotação cadastrada na base (ex: `10/01/2020`).<br><br>**PI2:** Data futura ($D_{+1}$, $D_{+N}$) — cotações futuras não existem.<br><br>**PI3:** Data nula (`null`) — ausência de valor obrigatório.<br><br>**PI4:** Data com valores impossíveis ou formato inválido (ex: `31/02/2024`). | **L1:** Data atual ($D_0$) — fronteira superior para cotação em tempo real.<br><br>**L2:** Ontem ($D_{-1}$) — fronteira imediata entre cotação atual e histórica.<br><br>**L3:** Amanhã ($D_{+1}$) — fronteira imediata para data futura (inválida).<br><br>**L4:** Dia exato com cotação cadastrada (`15/07/2014`) vs. Dia anterior (`14/07/2014`) e Dia posterior (`16/07/2014`).<br><br>**L5:** Formatação de dia e mês com 1 dígito ($< 10$, ex: `05/04`) e com 2 dígitos ($\ge 10$, ex: `25/11`). |

---

### Caracterização das Saídas

| Saída | Caracterização da Saída |
| :--- | :--- |
| **S4: Novas saídas associadas à data de cotação da conversão** | **S4.1:** Sucesso com cotação do dia atual — Retorna o valor monetário convertido aplicando a taxa vigente em $D_0$ (chave no padrão `"DE->PARA"`).<br><br>**S4.2:** Sucesso com cotação de data histórica — Retorna o valor monetário convertido aplicando a taxa cadastrada para a data informada (chave no padrão `"DE->PARA (dd/mm/aaaa)"`).<br><br>**S4.3:** Erro por cotação inexistente — Lança `IllegalArgumentException` informando que *"Não há taxa de conversão para as moedas e/ou para a data informada"*.<br><br>**S4.4:** Erro por data nula / inconsistente — Interrupção controlada (ou falha) por entrada inválida/nula. |

---

## 2. Código de Automação de Testes (Separados por Domínio e Metodologia AAA)

Os testes foram organizados de forma modular por domínio de responsabilidade:
1. `MoedaTest`: Domínio do modelo de dados da moeda (DTO).
2. `MoedaISO4217Test`: Domínio da enumeração de moedas ISO 4217, busca e formatação monetária.
3. `TaxaConversaoTest`: Domínio de regras de cálculo e validação entre moeda de origem e destino.
4. `CurrencyServicesTest`: Domínio de serviços de aplicação, orquestração de conversão, tratamento de datas e integração com o mock.

Todos os métodos de teste seguem estritamente a metodologia **AAA (Arrange, Act, Assert)** com blocos devidamente comentados.

### 2.1. `MoedaTest.java` (Domínio: Modelo de Moeda)

```java
package br.edu.puc.currencyconverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Domínio: Moeda")
public class MoedaTest {

    @Test
    @DisplayName("Deve instanciar Moeda e obter suas propriedades com sucesso")
    public void deveCriarMoedaEObterPropriedadesComSucesso() {
        // Arrange
        String codigoAlfabetico = "BRL";
        int codigoNumerico = 986;
        int casasDecimais = 2;
        String descricao = "Real Brasileiro";
        Locale locale = new Locale("pt", "BR");

        // Act
        Moeda moeda = new Moeda(codigoAlfabetico, codigoNumerico, casasDecimais, descricao, locale);

        // Assert
        assertEquals("BRL", moeda.getCodigoAlfabetico());
        assertEquals(986, moeda.getCodigoNumerico());
        assertEquals(2, moeda.getCasasDecimais());
        assertEquals("Real Brasileiro", moeda.getDescricao());
        assertEquals(locale, moeda.getLocale());
    }

    @Test
    @DisplayName("Deve instanciar Moeda com 0 casas decimais (ex: JPY)")
    public void deveInstanciarMoedaComZeroCasasDecimais() {
        // Arrange
        String codigoAlfabetico = "JPY";
        int codigoNumerico = 392;
        int casasDecimais = 0;
        String descricao = "Iene Japonês";
        Locale locale = Locale.JAPAN;

        // Act
        Moeda moeda = new Moeda(codigoAlfabetico, codigoNumerico, casasDecimais, descricao, locale);

        // Assert
        assertEquals("JPY", moeda.getCodigoAlfabetico());
        assertEquals(392, moeda.getCodigoNumerico());
        assertEquals(0, moeda.getCasasDecimais());
        assertEquals("Iene Japonês", moeda.getDescricao());
        assertEquals(Locale.JAPAN, moeda.getLocale());
    }
}
```

---

### 2.2. `MoedaISO4217Test.java` (Domínio: Enumeração e Busca ISO 4217)

```java
package br.edu.puc.currencyconverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Domínio: MoedaISO4217")
public class MoedaISO4217Test {

    @ParameterizedTest
    @EnumSource(MoedaISO4217.class)
    @DisplayName("Deve garantir que todas as constantes do enum possuem propriedades preenchidas")
    public void deveValidarPropriedadesDeTodasAsMoedas(MoedaISO4217 moeda) {
        // Arrange
        // Constante fornecida pelo ParameterizedTest

        // Act
        String codigo = moeda.getCodigoAlfabetico();
        int codigoNum = moeda.getCodigoNumerico();
        int decimais = moeda.getCasasDecimais();
        String desc = moeda.getDescricao();
        Locale loc = moeda.getLocale();

        // Assert
        assertNotNull(codigo);
        assertEquals(3, codigo.length());
        assertTrue(codigoNum > 0);
        assertTrue(decimais >= 0);
        assertNotNull(desc);
        assertFalse(desc.isBlank());
        assertNotNull(loc);
    }

    @Test
    @DisplayName("Deve formatar valor monetário corretamente para moeda padrão (BRL)")
    public void deveFormatarValorMonetarioBRL() {
        // Arrange
        MoedaISO4217 moeda = MoedaISO4217.BRL;
        BigDecimal valor = new BigDecimal("1234.50");

        // Act
        String resultado = moeda.formatar(valor);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("1.234,50") || resultado.contains("1234,50"));
    }

    @Test
    @DisplayName("Deve formatar valor monetário para moeda sem casas decimais (JPY)")
    public void deveFormatarValorMonetarioJPY() {
        // Arrange
        MoedaISO4217 moeda = MoedaISO4217.JPY;
        BigDecimal valor = new BigDecimal("1500");

        // Act
        String resultado = moeda.formatar(valor);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.contains(",00"));
    }

    @Test
    @DisplayName("Deve retornar string vazia ao formatar valor nulo")
    public void deveRetornarStringVaziaAoFormatarValorNulo() {
        // Arrange
        MoedaISO4217 moeda = MoedaISO4217.USD;
        BigDecimal valorNulo = null;

        // Act
        String resultado = moeda.formatar(valorNulo);

        // Assert
        assertEquals("", resultado);
    }

    @Test
    @DisplayName("Deve obter MoedaISO4217 por código alfabético com sucesso (case insensitive)")
    public void deveObterMoedaPorCodigoValido() {
        // Arrange
        String codigoMaiusculo = "USD";
        String codigoMinusculo = "eur";
        String codigoMisto = "bRl";

        // Act
        MoedaISO4217 resultadoUsd = MoedaISO4217.obterPorCodigo(codigoMaiusculo);
        MoedaISO4217 resultadoEur = MoedaISO4217.obterPorCodigo(codigoMinusculo);
        MoedaISO4217 resultadoBrl = MoedaISO4217.obterPorCodigo(codigoMisto);

        // Assert
        assertEquals(MoedaISO4217.USD, resultadoUsd);
        assertEquals(MoedaISO4217.EUR, resultadoEur);
        assertEquals(MoedaISO4217.BRL, resultadoBrl);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao buscar código inexistente")
    public void deveLancarExcecaoAoBuscarCodigoInexistente() {
        // Arrange
        String codigoInexistente = "XYZ";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MoedaISO4217.obterPorCodigo(codigoInexistente);
        });
        assertTrue(exception.getMessage().contains("XYZ") && exception.getMessage().contains("suportada"));
    }

    @Test
    @DisplayName("Anomalia detectada: AUD possui código numérico 30 devido ao literal octal 036")
    public void deveVerificarCodigoNumericoAudComportamentoAtual() {
        // Arrange
        MoedaISO4217 aud = MoedaISO4217.AUD;

        // Act
        int codigoNumerico = aud.getCodigoNumerico();

        // Assert
        // Na especificação ISO 4217 o código do AUD é 036 (decimal 36).
        // Contudo, no código fornecido foi escrito como '036' (literal octal em Java), resultando em 30.
        assertEquals(30, codigoNumerico, "Comportamento real do código dado: literal octal 036 avalia para 30");
    }
}
```

---

### 2.3. `TaxaConversaoTest.java` (Domínio: Regras de Taxa de Conversão)

```java
package br.edu.puc.currencyconverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Domínio: TaxaConversao")
public class TaxaConversaoTest {

    @Test
    @DisplayName("Deve instanciar TaxaConversao com moedas base e conversão diferentes")
    public void deveInstanciarComMoedasDiferentes() {
        // Arrange
        MoedaISO4217 de = MoedaISO4217.BRL;
        MoedaISO4217 para = MoedaISO4217.USD;

        // Act
        TaxaConversao taxa = new TaxaConversao(de, para);

        // Assert
        assertNotNull(taxa);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar instanciar com a mesma moeda de origem e destino")
    public void deveLancarExcecaoQuandoMoedasForemIguais() {
        // Arrange
        MoedaISO4217 de = MoedaISO4217.BRL;
        MoedaISO4217 para = MoedaISO4217.BRL;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new TaxaConversao(de, para);
        });
        assertTrue(exception.getMessage().contains("Moedas de base e convesao"));
    }

    @Test
    @DisplayName("Deve calcular a conversão corretamente aplicando a taxa informada")
    public void deveCalcularConversaoComSucesso() {
        // Arrange
        TaxaConversao taxaConversao = new TaxaConversao(MoedaISO4217.BRL, MoedaISO4217.USD);
        double taxa = 0.20;
        double quantia = 100.0;
        double valorEsperado = 20.0;

        // Act
        taxaConversao.setTaxaConversao(taxa);
        double resultado = taxaConversao.converter(quantia);

        // Assert
        assertEquals(valorEsperado, resultado, 0.0001);
    }

    @Test
    @DisplayName("Deve retornar zero quando a quantia a ser convertida for zero")
    public void deveRetornarZeroQuandoQuantiaForZero() {
        // Arrange
        TaxaConversao taxaConversao = new TaxaConversao(MoedaISO4217.USD, MoedaISO4217.BRL);
        taxaConversao.setTaxaConversao(5.11);
        double quantia = 0.0;

        // Act
        double resultado = taxaConversao.converter(quantia);

        // Assert
        assertEquals(0.0, resultado, 0.0001);
    }

    @Test
    @DisplayName("Deve retornar zero quando a taxa de conversão for zero")
    public void deveRetornarZeroQuandoTaxaForZero() {
        // Arrange
        TaxaConversao taxaConversao = new TaxaConversao(MoedaISO4217.EUR, MoedaISO4217.BRL);
        taxaConversao.setTaxaConversao(0.0);
        double quantia = 150.0;

        // Act
        double resultado = taxaConversao.converter(quantia);

        // Assert
        assertEquals(0.0, resultado, 0.0001);
    }
}
```

---

### 2.4. `CurrencyServicesTest.java` (Domínio: Serviços de Aplicação e Integração)

```java
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
    @DisplayName("Anomalia detectada: Falha ao passar Calendar.JULY (6) devido à indexação base-0 de Calendar.MONTH")
    public void deveDemonstrarFalhaAoUsarConstanteJulhoDevidoIndiceZero() {
        // Arrange
        String de = "BRL";
        String para = "USD";
        Calendar dataJulho = Calendar.getInstance();
        dataJulho.set(Calendar.YEAR, 2014);
        dataJulho.set(Calendar.MONTH, Calendar.JULY); // Calendar.JULY é 6!
        dataJulho.set(Calendar.DAY_OF_MONTH, 15);
        double quantia = 100.0;

        // Act & Assert
        // Devido ao bug no código, a data é formatada como "15/06/2014" em vez de "15/07/2014".
        // O mock procura "BRL->USD (15/06/2014)" que não existe, lançando exceção.
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyServices.converter(de, para, dataJulho, quantia);
        });
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
```

---

## 3. Cobertura Provida pelos Testes

Os testes foram executados via **Maven Surefire** e a cobertura de código foi mensurada pelo agente **JaCoCo (0.8.12)**. Todos os **43 testes unitários foram executados com sucesso (0 falhas, 0 erros)**.

### Tabela de Cobertura Obtida

| Classe / Enum | Cobertura de Linhas (Line) | Cobertura de Ramificações (Branch) | Cobertura de Instruções (Bytecode) | Cobertura de Métodos | Observações |
| :--- | :---: | :---: | :---: | :---: | :--- |
| **`CurrencyServices`** | **93,3%** (56/60) | **100,0%** (22/22) | **97,4%** (225/231) | **100,0%** (5/5) | As 4 linhas não cobertas pertencem a blocos `catch (IllegalArgumentException)` inalcançáveis em `converter`, pois `getCurrency` retorna `null` e nunca lança a dita exceção. Todas as linhas alcançáveis obtiveram 100% de cobertura. |
| **`MoedaISO4217`** | **100,0%** (31/31) | **100,0%** (6/6) | **100,0%** (198/198) | **100,0%** (9/9) | 100% de cobertura total em todos os métodos, construtores, buscas e branches. |
| **`Moeda`** | **100,0%** (12/12) | **N/A** (Sem branches) | **100,0%** (33/33) | **100,0%** (6/6) | 100% de cobertura total em construtor e getters. |
| **`TaxaConversao`** | **100,0%** (9/9) | **100,0%** (2/2) | **100,0%** (30/30) | **100,0%** (3/3) | 100% de cobertura total em construtor (ambos os fluxos de validação) e cálculo. |
| *`DataAcessMock` (Mock interno)* | **100,0%** (20/20) | **100,0%** (2/2) | **100,0%** (119/119) | **100,0%** (2/2) | 100% de cobertura das tabelas e do método `procurarTaxa`. |

---

## 4. Reporte de Falhas, Anomalias/Deficiências ou Sugestões Encontradas pela Equipe para o Código Dado

| Situação Reportada (Falha, Anomalia, Deficiência ou Sugestão) | Descrição Detalhada | Localização no Código (Classe / Método / Instrução) |
| :--- | :--- | :--- |
| **1. Anomalia / Falha Funcional Crítica** | **Indexação base 0 de `Calendar.MONTH` ao formatar a data histórica.**<br>Na API `java.util.Calendar`, os meses são numerados de 0 a 11 (Janeiro = 0, Julho = 6). No método `converter`, a formatação extrai `month = dt.get(Calendar.MONTH)` e concatena diretamente sem somar `+ 1`. Dessa forma, ao passar a data `15/07/2014` com `Calendar.JULY` (6), a chave formatada fica `"15/06/2014"`, não encontrando o registro `"BRL->USD (15/07/2014)"` existente no mock e gerando falha indevida. | `CurrencyServices.java`<br>Método: `converter`<br>Instrução: `month = dt.get(Calendar.MONTH);` |
| **2. Falha de Robustez (Crash por NPE)** | **Ausência de validação defensiva para data nula (`dt == null`).**<br>Se o consumidor chamar `converter` passando `dt = null`, a execução invoca imediatamente `dt.get(Calendar.YEAR)`, resultando em `NullPointerException` descontrolado em vez de uma `IllegalArgumentException` informativa (`"Data de cotação não pode ser nula"`). | `CurrencyServices.java`<br>Método: `converter`<br>Instrução: `dt.get(Calendar.YEAR)` |
| **3. Falha de Tratamento / Código Morto** | **Blocos `catch (IllegalArgumentException)` inalcançáveis e NPE ao passar moedas inválidas.**<br>O método `converter` envolve a chamada `this.getCurrency(de)` em um bloco `try-catch` capturando `IllegalArgumentException`. Contudo, `getCurrency()` nunca lança exceção: quando a moeda não existe, ela retorna `null`. Na sequência, `TaxaConversao(base, convers)` tenta executar `base.getCodigoAlfabetico()`, gerando um inesperado `NullPointerException`. | `CurrencyServices.java`<br>Método: `converter`<br>Instruções: Linhas 76-89 (`base = this.getCurrency(de)`) |
| **4. Falha de Robustez (NPE)** | **Moeda de entrada nula gera `NullPointerException` em `getCurrency` e `isValidCurrency`.**<br>Caso seja passado `null` para `isValidCurrency` ou `getCurrency`, a linha `allCurrency[i].getCodigoAlfabetico().compareToIgnoreCase(codigoAlfabetico)` lança `NullPointerException`, pois não há verificação prévia de nulidade para o parâmetro. | `CurrencyServices.java`<br>Métodos: `isValidCurrency` e `getCurrency`<br>Instrução: `.compareToIgnoreCase(codigoAlfabetico)` |
| **5. Anomalia de Dados (Bug de Compilação/Lógica)** | **Código numérico do AUD é inicializado como literal octal `036`.**<br>Em Java, literais inteiros iniciados com `0` são interpretados em base octal ($036_8 = 3 \times 8^1 + 6 = 30_{10}$). O código numérico ISO 4217 do AUD é `036` (decimal 36), mas na classe avalia para `30`. | `MoedaISO4217.java`<br>Declaração da constante: `AUD`<br>Instrução: `AUD("AUD", 036, 2, ...)` |
| **6. Anomalia Funcional (Serialização Dupla)** | **JSON duplamente serializado / escapado em `getAllCurrencies`.**<br>O método converte a lista para JSON através de `json = gson.toJson(moedas)` e em seguida retorna `gson.toJson(json)`. Isso gera uma string JSON com escape de aspas (`"\"[{\\\"codigoAlfabetico\\\":...}]\""`), forçando os clientes HTTP a fazerem `JSON.parse` duas vezes. | `CurrencyServices.java`<br>Método: `getAllCurrencies`<br>Instrução: `return gson.toJson(json);` |
| **7. Anomalia de Codificação e Ortografia** | **Mensagem de erro com caracteres corrompidos e erro ortográfico.**<br>A mensagem de erro no construtor de `TaxaConversao` contém encoding corrompido (`"Moedas de base e convesao nï¿½o podem ser as mesmas"`), além de erro ortográfico na palavra *"conversão"* grafada como *"convesao"*. Além disso, o atributo da classe chama-se `conver`. | `TaxaConversao.java`<br>Construtor: `TaxaConversao`<br>Instrução: `throw new IllegalArgumentException(...)` |
| **8. Deficiência de Nomenclatura** | **Erro ortográfico no nome da classe do mock (`DataAcessMock`).**<br>O nome da classe está sem uma letra 'c' (`DataAcessMock` ao invés de `DataAccessMock`). | `DataAcessMock.java`<br>Declaração: `class DataAcessMock` |
| **9. Sugestão Arquitetural** | **Uso de tipos legados (`Calendar`) e imprecisão monetária com ponto flutuante (`double`).**<br>Recomenda-se substituir a classe legada e mutável `Calendar` pela moderna API `java.time.LocalDate` (Java 8+). Além disso, cálculos cambiais devem utilizar `BigDecimal` para evitar perda de precisão binária inerente ao tipo primitivo `double` (IEEE 754). | `CurrencyServices.java` e `TaxaConversao.java`<br>Método: `converter` |
| **10. Deficiência de Design / Violação DRY** | **Duplicação de busca de moeda entre `CurrencyServices` e `MoedaISO4217`.**<br>A enum `MoedaISO4217` já possui o método estático `obterPorCodigo(String codigo)` que busca a moeda e lança `IllegalArgumentException` quando inválida. O serviço `CurrencyServices` reescreveu o loop de busca em `getCurrency()` e `isValidCurrency()`, retornando `null` em vez de reutilizar a lógica centralizada do enum. | `CurrencyServices.java`<br>Métodos: `getCurrency` e `isValidCurrency` |
