package br.edu.puc.currencyconverter;

//modificador de acesso defaut, classe interna apenas ao pacote
class TaxaConversao {

	private MoedaISO4217 base;
	private MoedaISO4217 conver;
	private double taxa;
	
	public TaxaConversao(MoedaISO4217 de, MoedaISO4217 para) {
		super();
		this.base = de;
		this.conver = para;
		
		//verifica se sao a mesma moeda
		if (base.getCodigoAlfabetico().compareToIgnoreCase(para.getCodigoAlfabetico()) == 0)
			throw new IllegalArgumentException("Moedas de base e convesao nï¿½o podem ser as mesmas");	
	}
	
	public void setTaxaConversao (double taxa) {
		this.taxa = taxa;
	}
	
	public double converter(double quantia) {
		return quantia * this.taxa;
	}
	

}
