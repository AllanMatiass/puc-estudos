package br.edu.puc.currencyconverter;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.util.Calendar;
import java.time.LocalDate;

//modificador de acesso defaut, classe interna apenas ao pacote
class DataAcessMock{

	private Map<String, Double> dados;
	
	public DataAcessMock() { //construtor gera os dados mocados
			
			//cria um HashMap com as moedas (chave) e outro hashMap
			dados = new HashMap<> ();
						
			//popula HashMap c/ algumas taxas de conversão de REAL para 5 outras moedas
			//formato da chave (codigo 3 caracteres) "BASE->CONVERSAO (data anterior)"
			//sem data é a cotação do momento (hoje)
	
			dados.put("BRL->USD",0.20d);
			dados.put("BRL->USD (15/07/2014)",0.45);
					
			dados.put("BRL->GBP",0.14d);  		
			dados.put("BRL->GBP (15/07/2014)",0.26d);  		
										
			dados.put("BRL->EUR",0.17d);	
			dados.put("BRL->EUR (15/07/2014)",0.33d);
			
			dados.put("BRL->CHF",0.16d);			

			dados.put("BRL->CAD",0.27d);
			
			//popula HasMap c/algumas taxas de conversão das 5 outras moedas para o Real
		
			dados.put("USD->BRL",5.11d);
			
			dados.put("EUR->BRL",5.86d);		

			dados.put("GBP->BRL",6.83d);  		
				
			dados.put("CHF->BRL",6.22d);			
				
			dados.put("CAD->BRL",3.64d);
					
	}
	
	//método que substitui a query de acesso ao BD real
	public double procurarTaxa(String chave){
	 
			if (dados.containsKey(chave)) 
			{	String st = dados.get(chave).toString();
				return Double.parseDouble(st);
			}
			throw new IllegalArgumentException("Não há taxa de conversão para as moedas e/ou para a data informada");
	}
}

