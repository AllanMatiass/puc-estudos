from redis import Redis
from service.mongo_service import getMongoContext
from json import dumps, loads

r = Redis(host='localhost', port=6379, decode_responses=True)



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

    return student

if (__name__ == '__main__'):
    cache_fill()

    res = search_student('camilarodrigueslima:345')

    if (res):
        print(res)
    else:
        print('encontrou nao')

