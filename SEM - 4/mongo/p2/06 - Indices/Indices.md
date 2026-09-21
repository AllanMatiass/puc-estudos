
## Índices são usados para acelerar buscas

1. Índices nem sempre trazem alto desempenho nas buscas.
2. Em todo banco, se faz necessário projetar com **CUIDADO** o uso de índices. Pois o banco de dados faz uma análise de qual índice usar entre muitos.


# IXScan - Scan de Índice
significa que o mongo buscou dado pelo índice, não diretamente pela coleção.

# COLLSCAN - Scan de collections
Significa que o mongo buscou pela collection inteira


### Quando criamos um índice, escolhemos campos que se repetem
para ter menos conjunto de dados e mais elementos em cada conjunto.

