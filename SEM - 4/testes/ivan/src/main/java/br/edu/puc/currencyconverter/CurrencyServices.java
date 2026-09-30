package br.edu.puc.currencyconverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Calendar;


//Classe com modificador de acesso public, funciona coma API do Pacote
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class CurrencyServices {
	
	//objeto Mock de acesso aos dados, private e inacessivel
	private DataAcessMock daMock = new DataAcessMock(); 
	
	public String getAllCurrencies(){ //retona json com todas as moedas da Enum)
		String json = "";
		
		MoedaISO4217[] allCurrency = MoedaISO4217.values();
		
		//constroi as instâncias de moeda
		List<Moeda> moedas = new ArrayList<Moeda>();
		
		//int i=0;

		for (MoedaISO4217 ac: allCurrency) {
		
			moedas.add ( new Moeda(ac.getCodigoAlfabetico(),
					ac.getCodigoNumerico(),
					ac.getCasasDecimais(),
					ac.getDescricao(),
					ac.getLocale() 
					));
		}
		
		Gson gson = new Gson();
		json = gson.toJson(moedas);
        return gson.toJson(json);
	}	
	
	public boolean isValidCurrency(String codigoAlfabetico) {
		
		MoedaISO4217[] allCurrency = MoedaISO4217.values();
		
		for (int i=0; i<allCurrency.length;i++)
			if (allCurrency[i].getCodigoAlfabetico().compareToIgnoreCase(codigoAlfabetico)==0)
				return true;
				
		return false;//não achou o código de moeda passado como parâmetro
	}
	
	public MoedaISO4217 getCurrency(String codigoAlfabetico) {
		
		MoedaISO4217[] allCurrency = MoedaISO4217.values();
		
		for (int i=0; i<allCurrency.length;i++)
			if (allCurrency[i].getCodigoAlfabetico().compareToIgnoreCase(codigoAlfabetico)==0)
				return allCurrency[i];
				
		return null;//não achou o código de moeda passado como parâmetro
	}

	
	public double converter(String de, String para, Calendar dt, double quantia) {
		
		MoedaISO4217 base=null, convers=null;

		
		//valida moeda base
	   	try {
	    	 base = this.getCurrency(de) ; 
	    	} 
	   	catch (IllegalArgumentException e) {
	   		throw e;
	    	}
		
		//valida moeda convesão
	   	try {
	    	  convers = this.getCurrency(para); 
	    	} 
	   	catch (IllegalArgumentException e) {
	   	           throw e;
	    	}
	   	   	
	   	//constroi a instância de taxa de conversao 
	   	TaxaConversao taxa;
	   	
	   	try {
	   			taxa = new TaxaConversao(base,convers);
	    	} 
	   	catch (IllegalArgumentException e) {
	   	           throw e; //atira a exceção do construtor de Taxa
	    	}
	   	
	   	if (quantia < 0)
			throw new IllegalArgumentException("Quantia informada é inválida");
	   	
		//monta as String que é chave de busca no Data Acess mock 
	   	//String chave = de+"->"+para+" (dd/mm/aaaa)";
	   	String chave = de+"->"+para;

	   	Calendar hoje = Calendar.getInstance();

	   	boolean eHoje = (dt.get(Calendar.YEAR) == hoje.get(Calendar.YEAR)) &&
	   	                 (dt.get(Calendar.DAY_OF_YEAR) == hoje.get(Calendar.DAY_OF_YEAR));
	   	
	   	//
	   	int day, month; 
	   	
	   	if (!eHoje) //não é hoje, concatena a data parametro na string da chave
	   	{   
	   		chave = chave + " ("; //inicia concatenação da data na chave
	   	    day = dt.get(Calendar.DAY_OF_MONTH);
	   		if (day <10)
	   			chave = chave + "0"+day;
	   			else chave = chave + day;
	   		
	   		chave = chave + "/";
	   		month = dt.get(Calendar.MONTH);
	   		if (month <10)
	   			chave = chave + "0"+month;
	   			else chave = chave + month;
	   		
	   		chave = chave + "/";
	   		chave = chave + dt.get(Calendar.YEAR);
	   		
	   		chave = chave + ")"; //fim da formatação da String
	   		
	   	}
		 	
	   	//busca a taxa no Mock do Banco de Dados
	   	double tx = daMock.procurarTaxa(chave);
	   	
	   	//seta a taxa na Intancia de TaxaDeConversao
	   	taxa.setTaxaConversao(tx);
	   	
	   	//executa ou aciona a conversao  	
	   	return taxa.converter(quantia);
	}
		
}
