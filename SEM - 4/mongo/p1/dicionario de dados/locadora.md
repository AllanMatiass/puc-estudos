
# Título - Dicionário de dados da locadora de veiculos automotivos da empresa Não sei o que / S.A


## Entidades

### 1. Cliente

Descrição: Representa uma pessoa física ou pessoa jurídica que aluga um ou mais veículos ao longo do tempo. É importante destacar, que cliente que nunca alugaram um veículo, devem ser representados.

Atributos: 

|Nome|Tipo|Obrigatoriedade|Exemplos/Comentários|
|----|----|---------------|--------|
|Código|Inteiro Único|Sim| São aceitos números de 1 a (inteiro longo maior possível), acrescidos de 1 em 1|
|Nome|String Livre Sem Caracteres ESpeciais |Sim|Campo que armazenará pela primeira vez o nome do cliente, após a emissão da primeira nota fiscal, o nome/razão social é corrigido de acordo com o leão malvado (Receita federal). Sempre o nome será armazenado em maiúsculo para não ter variações ou problemas de formatação em relatórios e listas.|
|Tipo|Lista literal, estática e definida de valores [Física, Jurídica]|Sim|A lista é composta por dois valores: Física, Jurídica. Não devem ser acrescentados valores|
|Documento|Par chave-valor|Sim|Chave: é uma lista estatica contendo CPF/CNPJ/Passaporte (pesquisar se passaporte serve para emissão de um documento fiscal caso o cliente seja estrangeiro). Valor: é o conteúdo textual do documento com formatação própria|
