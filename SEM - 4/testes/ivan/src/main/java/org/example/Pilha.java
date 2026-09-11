package org.example;

public class Pilha {
	
	private int dados[];
	private int tamanho, atual;
	
	public Pilha(){ //constroi pilha default tamanho 10
		tamanho = 10;
		dados = new int[10];
		atual = 0;
	}
	
	public Pilha (int c){ //constroi pilha de capacidade c
		tamanho = c;
		dados = new int[c];
		atual = 0;
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public int getCapacidadeLivre() {
		return tamanho - atual;
	}
	
	public boolean isFull() {
		if (atual < tamanho)  return false;
		return true;
	}
	
	public boolean isEmpty() {
		if (atual < 1)  return true;
		return false;
	}
	
	public boolean push (int valor) {	
		if (this.isFull()) 
			return false;
		
		dados[atual++] = valor;
		return true;
	}
	
	public RetornoPilha pop () {	
		if (this.isEmpty()) 
			return new RetornoPilha(0, true);
			
		RetornoPilha r = new RetornoPilha(dados[--atual],  this.isEmpty() ? true : false );
		return (r);
	}
	
}
