from redis import Redis

r = Redis(host='localhost', port=6379, decode_responses=True)

r.set('bike:id:1', f'{{"testechave": "cu"}}')

print(r.get('bike:id:1'))

print(r.get('aprovado:1'))

# Guardar em cache alguns estudantes aleatorios
# testar no redis com vários tipos de dados (string, float, byte, int)

# parte 2
# considere o cenário:
'''

# parte 1
um site para divulgação de resultados de vestibular. 
Este site tem o mesmo problema de um outro para divulgar resultados de concursos publicos.
o que muda é somente a escala (qtd de dados).


# parte 2
crie um banco de dados mongodb chamado vestibular e uma coleção aprovados. nesta coleção coloque 15 estudantes aprovados com os seguintes dados:

{
    nomeCompleto: string,
    finalCPF: string,
    cursos: string[],
    classificacaoGeral: int,
    classificacoesesCursos: 
}

distribua esses estudantes em apenas 3 cursos
em seguida, faça um programa em python ou java que acesse os dados do mongo e popule o redis.
Este conceito chamamos de cache fill

melhore seu programa em python criando 2 funcoes
uma chamada cache_fill() e outra chamada search_student(key)

# parte 3

se vc se animar, transforme este pequeno programa em python em um backend de verdade usando python flask

para testar esse backend, não precisa de um frontend. Use o postman

'''