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
    @DisplayName("Validação de Entradas Inválidas no Enum: Strings vazias ou com espaços devem lançar exceção")
    public void deveLancarExcecaoParaCodigosVaziosOuComEspacos() {
        assertThrows(IllegalArgumentException.class, () -> MoedaISO4217.obterPorCodigo(""));
        assertThrows(IllegalArgumentException.class, () -> MoedaISO4217.obterPorCodigo(" BRL "));
        assertThrows(IllegalArgumentException.class, () -> MoedaISO4217.obterPorCodigo("US"));
    }

    @Test
    @DisplayName("Formatação Monetária com Zero e Negativo")
    public void deveFormatarCorretamenteZeroENegativo() {
        // Arrange
        MoedaISO4217 usd = MoedaISO4217.USD;

        // Act & Assert
        String formatadoZero = usd.formatar(BigDecimal.ZERO);
        assertNotNull(formatadoZero);
        assertTrue(formatadoZero.contains("0.00") || formatadoZero.contains("0,00"));

        String formatadoNegativo = usd.formatar(new BigDecimal("-10.50"));
        assertNotNull(formatadoNegativo);
        assertTrue(formatadoNegativo.contains("10.50") || formatadoNegativo.contains("10,50"));
    }

    @Test
    @DisplayName("ANOMALIA: AUD possui código numérico 30 devido ao literal octal 036")
    public void deveVerificarCodigoNumericoAudComportamentoAtual() {

        // Arrange
        MoedaISO4217 aud = MoedaISO4217.AUD;

        // Act
        int codigoNumerico = aud.getCodigoNumerico();

        System.out.println(
                "AVISO: Moeda AUD deveria possuir código numérico 36, " +
                        "porém o literal '036' foi interpretado como octal, resultando em: "
                        + codigoNumerico
        );


        // Assert
        // Na especificação ISO 4217 o código do AUD é 036 (decimal 36).
        // Contudo, no código fornecido foi escrito como '036' (literal octal em Java), resultando em 30.
        assertEquals(30, codigoNumerico, "Comportamento real do código dado: literal octal 036 avalia para 30");
    }
}
