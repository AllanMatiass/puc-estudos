def contar_repetidos(lista):
    vistos = {}
    for i in lista:
        if vistos[i]:
            vistos[i] = vistos[i] + 1
        else:
            vistos[i] = 1

    for i in lista:
        if vistos[i] > 1:
            print(i)
