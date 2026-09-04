# Tópicos abordados

O que é uma trhead (com código em c usando a lib `pthreads` e "natural" do C)

# Escalonador da CPU
Visão geral
Escalonamento preemptivo e não preemptivo

para tomar decisão sobre escalonamento a gente tem alguns criterios:

- utilização de cpu, mantendo-a mais ocupada possível
- throughput - # de processos completados em um intervalo de tempo
- Tempo de turnaround - tempo necessario para executar um processo especifico
- tempo de espera - tempo que o processo esta esperando na fila de *prontos*
- tempo de resposta - tempo levado para que seja produzida uma primeira resposta para uma requisição

# Dispatcher
Como o nome sugere, ele altera processos como um proprio dispatcher, ele MANDA nos processos, ele decide qual processo usar e quando usar.


# Formulas
para cada processo
turnaround = `fim - chegada`
espera = `turnaround - pico`
resposta = `primeira execução - chegada`


# Escalonamento FCFS
ordena por ordem de chegada

# Escalonamento SJF - Shortest Job First
Ordena os processos dos menores para os maiores processos

# Escalonamento SRTF - Shortest Remaining Time First
Bucha demais, precisa estudar.
tem o preemptivo e o não preemptivo
