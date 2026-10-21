import os
from dotenv import load_dotenv
from pymongo import MongoClient

load_dotenv('./.env')

uri = os.getenv('URI_MONGO')

def getMongoContext():


    mongo = MongoClient(uri)
    db = mongo['vestibular']
    aprovados_col = db['aprovados']
    return (mongo, db, aprovados_col)



estudantes = [
    {
        "nomeCompleto": "Ana Silva Santos",
        "finalCPF": "123",
        "cursos": ["Ciência da Computação"],
        "classificacaoGeral": 1,
        "classificacoesesCursos": [
            {"curso": "Ciência da Computação", "classificacao": 1}
        ]
    },
    {
        "nomeCompleto": "Bruno Oliveira Costa",
        "finalCPF": "234",
        "cursos": ["Engenharia de Software"],
        "classificacaoGeral": 2,
        "classificacoesesCursos": [
            {"curso": "Engenharia de Software", "classificacao": 1}
        ]
    },
    {
        "nomeCompleto": "Camila Rodrigues Lima",
        "finalCPF": "345",
        "cursos": ["Sistemas de Informação"],
        "classificacaoGeral": 3,
        "classificacoesesCursos": [
            {"curso": "Sistemas de Informação", "classificacao": 1}
        ]
    },
    {
        "nomeCompleto": "Daniel Pereira Souza",
        "finalCPF": "456",
        "cursos": ["Ciência da Computação", "Sistemas de Informação"],
        "classificacaoGeral": 4,
        "classificacoesesCursos": [
            {"curso": "Ciência da Computação", "classificacao": 2},
            {"curso": "Sistemas de Informação", "classificacao": 2}
        ]
    },
    {
        "nomeCompleto": "Eduarda Martins Alves",
        "finalCPF": "567",
        "cursos": ["Engenharia de Software"],
        "classificacaoGeral": 5,
        "classificacoesesCursos": [
            {"curso": "Engenharia de Software", "classificacao": 2}
        ]
    },
    {
        "nomeCompleto": "Felipe Barbosa Carvalho",
        "finalCPF": "678",
        "cursos": ["Sistemas de Informação"],
        "classificacaoGeral": 6,
        "classificacoesesCursos": [
            {"curso": "Sistemas de Informação", "classificacao": 3}
        ]
    },
    {
        "nomeCompleto": "Gabriela Ramos Ribeiro",
        "finalCPF": "789",
        "cursos": ["Ciência da Computação"],
        "classificacaoGeral": 7,
        "classificacoesesCursos": [
            {"curso": "Ciência da Computação", "classificacao": 3}
        ]
    },
    {
        "nomeCompleto": "Henrique Gomes Ferreira",
        "finalCPF": "890",
        "cursos": ["Engenharia de Software", "Ciência da Computação"],
        "classificacaoGeral": 8,
        "classificacoesesCursos": [
            {"curso": "Engenharia de Software", "classificacao": 3},
            {"curso": "Ciência da Computação", "classificacao": 4}
        ]
    },
    {
        "nomeCompleto": "Isabela Castro Rocha",
        "finalCPF": "901",
        "cursos": ["Sistemas de Informação"],
        "classificacaoGeral": 9,
        "classificacoesesCursos": [
            {"curso": "Sistemas de Informação", "classificacao": 4}
        ]
    },
    {
        "nomeCompleto": "João Vitor Dias",
        "finalCPF": "012",
        "cursos": ["Ciência da Computação"],
        "classificacaoGeral": 10,
        "classificacoesesCursos": [
            {"curso": "Ciência da Computação", "classificacao": 5}
        ]
    },
    {
        "nomeCompleto": "Larissa Monteiro Mendes",
        "finalCPF": "135",
        "cursos": ["Engenharia de Software"],
        "classificacaoGeral": 11,
        "classificacoesesCursos": [
            {"curso": "Engenharia de Software", "classificacao": 4}
        ]
    },
    {
        "nomeCompleto": "Matheus Freitas Moreira",
        "finalCPF": "246",
        "cursos": ["Sistemas de Informação"],
        "classificacaoGeral": 12,
        "classificacoesesCursos": [
            {"curso": "Sistemas de Informação", "classificacao": 5}
        ]
    },
    {
        "nomeCompleto": "Natália Cardoso Teixeira",
        "finalCPF": "357",
        "cursos": ["Ciência da Computação", "Engenharia de Software"],
        "classificacaoGeral": 13,
        "classificacoesesCursos": [
            {"curso": "Ciência da Computação", "classificacao": 6},
            {"curso": "Engenharia de Software", "classificacao": 5}
        ]
    },
    {
        "nomeCompleto": "Otávio Nogueira Cavalcanti",
        "finalCPF": "468",
        "cursos": ["Engenharia de Software"],
        "classificacaoGeral": 14,
        "classificacoesesCursos": [
            {"curso": "Engenharia de Software", "classificacao": 6}
        ]
    },
    {
        "nomeCompleto": "Patrícia Azevedo Duarte",
        "finalCPF": "579",
        "cursos": ["Sistemas de Informação"],
        "classificacaoGeral": 15,
        "classificacoesesCursos": [
            {"curso": "Sistemas de Informação", "classificacao": 6}
        ]
    }
]

if (__name__ == '__main__'):

    mongo, db, aprovados_col = getMongoContext()
    
    aprovados_col.delete_many({})
    resultado = aprovados_col.insert_many(estudantes)

    print(f"Inseridos {len(resultado.inserted_ids)} estudantes na coleção 'aprovados'.")

    mongo.close()
