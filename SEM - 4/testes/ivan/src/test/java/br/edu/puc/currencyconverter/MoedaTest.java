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
