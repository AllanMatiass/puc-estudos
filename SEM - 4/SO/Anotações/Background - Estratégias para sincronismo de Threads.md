
# Race Condition
em caso de incompetência do dev, pode ocorrer uma race condition, ou seja, threads diferentes podem alterar os mesmos valores, fazendo com que o programa faça algo errado, alterando dados que não deveriam ser alterados.

# Estrutura padrão de threads

#### Estratégia 1 - Busy Waiting
```c
while (true) {
	flag[i] = true;
	turn = j;
	while(flag[i] && turn == j){
		
	}
	
	
// seção crítica - trecho de código quando trabalha com variável global
// geralmente usam flags para ver quem vai executar
	flag[i] = false;
	// fim seção cítica

// seção de saída - remainder section

}
```

#### Estratégia 2 - Semáforos com Busy Waiting (BW)
- Semaforo de contagem (quantas threads estão )
- Semáforo binário
- problema: 

```c

wait(S) {
	while (S <= 0); // busy/wait
	S--;
}


signal(S){
S++;
}

```

```c

int mutex = 1;
while(True){

wait(mutex);
// SC - semáforo de contagem
signal(mutex); // 

// remainder

}

```

#### Estratégia 3 - Semáforos sem BW

```c
wait(semaphore *S){
	S -> value--;
	if (S->value < 0) {
	// add this proccess to s->list
	block(P); // esperar até chegar a vez do processo
}


singal(semaphore *S){
	S->value++;
	if (S->value <= 0){
	// remove a proccess P from S->list;
	wakeup(P); // sinaliza que pode executar
}

// Sequencia correta:
wait(mutex);
signal(mutex);
```


