package br.edu.puc.currencyconverter;
import java.util.Locale;

 class Moeda {

	private String codigoAlfabetico;
	private int codigoNumerico, casasDecimais;
	private String descricao;
	private Locale locale;
	
	public Moeda(String codigoAlfabetico, int codigoNumerico, int casasDecimais, String descricao, Locale locale) {
		super();
		this.codigoAlfabetico = codigoAlfabetico;
		this.descricao = descricao;
		this.codigoNumerico = codigoNumerico;
		this.casasDecimais = casasDecimais;
		this.locale = locale;
	}
	public String getCodigoAlfabetico() {
		return codigoAlfabetico;
	}
	public String getDescricao() {
		return descricao;
	}
	public int getCodigoNumerico() {
		return codigoNumerico;
	}
	public int getCasasDecimais() {
		return casasDecimais;
	}
	public Locale getLocale() {
		return locale;
	}
}
