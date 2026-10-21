from redis import Redis
from mongo_service import getMongoContext
from json import dumps, loads

r = Redis(host='localhost', port=6379, decode_responses=True)

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
    classificacoesesCursos: [{
        curso: 'a',
        classificacao: 4,
    }]
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



mongo, db, collection = getMongoContext()
def cache_fill():
    students = collection.find({})
    for s in students:
        s.pop('_id', None)

        sid = s['finalCPF']
        name = s['nomeCompleto'].replace(' ', '').lower()
        key = f'student:{name}:{sid}'

        r.set(key, dumps(s))

def search_student(key: str):
    student = r.get(f'student:{key}')

    return loads(student) if student != None else None

if (__name__ == '__main__'):
    cache_fill()

    res = search_student('camilarodrigueslima:345')

    if (res):
        print(res)
    else:
        print('encontrou nao')

