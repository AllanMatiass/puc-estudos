
# O que é
O `pom.xml` é o arquivo de configuração principal de um projeto **Maven**.  
Ele define as **dependências**, plugins e configurações necessárias para o projeto.  
Também informa dados como nome, versão e tipo do projeto.  
Em resumo, ele controla **como o Maven gerencia e constrói o projeto**.

# Partes importantes:

## Dependencies

As `dependencies` são as **bibliotecas externas** que o projeto precisa para funcionar.  
O Maven é responsável por **baixar, gerenciar e disponibilizar** essas bibliotecas para o projeto.

Cada `dependency` possui algumas informações importantes:

- `groupId` → identifica a organização ou grupo que criou a biblioteca.
- `artifactId` → identifica o nome da biblioteca.
- `version` → define qual versão da biblioteca será utilizada.
- `scope` → define em quais situações a dependência estará disponível.

Exemplo:

`spring-boot-starter-web` é uma `dependency` que adiciona recursos necessários para criar aplicações web com Spring Boot.

## Plugins

Os `plugins` são **ferramentas que executam tarefas durante o processo de construção do projeto**.

Eles podem ser usados para:

- Compilar o código.
- Executar testes.
- Gerar o `.jar`.
- Executar a aplicação.
- Gerar documentação.
- Realizar outras tarefas automatizadas.

Assim como as `dependencies`, um plugin normalmente possui:

- `groupId` → identifica a organização.
- `artifactId` → identifica o plugin.
- `version` → define a versão do plugin.

**Resumindo:** `dependencies` são **bibliotecas que o seu código utiliza**, enquanto `plugins` são **ferramentas que o Maven utiliza para executar tarefas no projeto**.