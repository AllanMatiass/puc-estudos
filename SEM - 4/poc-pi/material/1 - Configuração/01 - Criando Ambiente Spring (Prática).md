Passo a passo pra criar um ambiente Spring:

- Acessar: https://start.spring.io/
- Configurar o projeto (lado esquerdo
- Adicionar dependencias
- Fazer download do arquivo e extrair aonde quiser

Todos os passos vão ser recomendados por mim abaixo

### Configuração do projeto

Para ficar mais fácil de explicar, vou usar as seguintes configurações:

Project: Maven (package manager e compilador)
Language: Java
Spring Boot: 4.1.0

Project metadata: qualquer coisa, mas, geralmente é a URL do seu domínio invertida (ex: br.com.matias)

Packaging: JAR (arquivo compilado)

Configuration: pessoal, mas prefiro YAML, pois é um pouco mais organizado e menos reduntante que properties. O propósito é exatamente o mesmo: gerenciar configurações da aplicação

Java: 21 (LTS mais recente)
![[config spring initializr.png]]

### Dependências Iniciais
Ao clicar em Add Dependencies, vc pode colocar algumas *dependencias iniciais* no seu projeto, você pode adicionar mais no arquivo `pom.xml`caso não exista na biblioteca do spring initializr. Vou explicar mais pra frente sobre.

As dependências que vamos usar pra esse POC (prova de conceito) estão listadas abaixo. (Inclusive, é super importante ter o docker desktop instalado)
![[dependencias.png]]

#### O que cada dependência faz?

##### Docker Compose Support:
Adiciona suporte com Docker Compose para o projeto, vou passar por cima, criando um mongo local para entendermos o conceito de docker compose.

##### Spring Boot DevTools:
Uma dependencia pra melhorar o DX (developer experience), tendo livereload e builds mais rápidos em ambientes de desenvolvimento.

##### Spring Web:
Essencial para criar a API (ou MVC, mas não vamos abordar MVC)

##### Spring Security:
Essencial caso sua API tenha autenticação, mas nesse POC a gente vai abordar só o conceito de configurações no Spring, praticamente um Middleware (vocês provavelmente usaram no PI 2)

##### Spring Data MongoDB:
Adiciona suporte do **Spring Data** para o Mongo. O **Spring Data** é um **ORM** para facilitar a integração e queries e configuração do banco em que for usado. E como o nome sugere, vamos usar o Suporte para o MongoDB.


### Finalização
Clicar em "generate" e extrair a pasta compactada para um ambiente adequado.

