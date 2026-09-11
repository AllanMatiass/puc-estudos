
import static org.junit.jupiter.api.Assertions.*;

import org.example.Pilha;
import org.example.RetornoPilha;
import org.junit.jupiter.api.Test;


class TesteCasePilha01 { 

	@Test
	void test01() { 
		//teste basico de pilha. 
		// Construir Pilha (tam>0 V1.2), push (valor inteiro V2.1) 
		// seguido de pop (S1.1)
		
		//arrange - prepara��o do teste
		Pilha p = new Pilha(5); //constroi pilha (V1.2)
		//action - duas chamadas  aos m�todos da pilha
		//assert - j� faz a primeira asser�ao
		assertTrue(p.push(10)); //aqui 1a chamada (V2.1) e 1a asser��o (S2.1)
		RetornoPilha ret = p.pop();
		
		//segundo assert (verifica��o valor esperado vs. valor real)
		assertEquals(10, ret.valor); //S1.1 - campo valor dp topo da pilha
        assertTrue(ret.vazia); //S1.1 - vampo vazio = true
	}

	@Test
	void test02() { 
		//testa push em pilha cheia (estouro de overflow da pilha)
		
		//prepara��o do teste
		Pilha p = new Pilha(2); //pilha tamanho 2 (V1.2)
		
		//chamada do m�todos e asser��o
		//enche a pilha e tenta fazer mais um push()
		assertTrue(p.push(10)); 
		assertTrue(p.push(20)); 
		assertFalse(p.push(30)); //aqui 3a chamada (V2.1) e 3a asser��o (S2.2) 
	}

	// V1.1 - Construtor Padrão, Inteiro inválido
	@Test
	void test03(){
		Pilha p = new Pilha();

        assertEquals(10, p.getTamanho());

		// Empilhar Número Inteiro invalido - v2.1
		// Possivelmente isso é um erro, o código da classe Pilha deve ser alterado para corrigir isso.
		assertFalse(p.push('c'));

		// Pop de 1 numero do
		assertTrue(p.pop().vazia);

		RetornoPilha rp = p.pop();
		// tenta fazer o pop de outro valor
		// assertEquals(rp.valor, null);



	}

}