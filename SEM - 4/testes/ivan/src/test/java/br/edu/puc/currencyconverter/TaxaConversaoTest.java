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
