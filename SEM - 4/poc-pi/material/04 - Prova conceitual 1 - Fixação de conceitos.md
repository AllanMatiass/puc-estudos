
````md
# 🚀 POC — Conceitos Básicos do Ambiente Spring

> Responda com suas próprias palavras. Não é necessário escrever código.

## 1. Projeto

Explique o que representa cada configuração:

- Maven
- Java
- JAR
- YAML
- Java 21

---

## 2. Estrutura

Explique brevemente a função de:

- `.idea` / `.vscode`
- `.mvn`
- `.gitignore`
- `.gitattributes`
- `mvnw` / `mvnw.cmd`
- `compose.yaml`
- `pom.xml`
- `src/main`
- `src/test`

---

## 3. `pom.xml`

Responda:

**a)** O que é o `pom.xml`?

**b)** Qual a diferença entre `dependencies` e `plugins`?

---

## 4. Dependências

Explique, de forma simples, para que servem:

- Docker Compose Support
- Spring Boot DevTools
- Spring Web
- Spring Security
- Spring Data MongoDB

---

## 5. Aplicação

Observe:

```java
@SpringBootApplication
public class PiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiApplication.class, args);
    }

}
````

Explique, de forma geral, o que esse código faz.

---

## 🚨 Pergunta final

Explique com suas palavras:

> Por que usamos `Spring Data MongoDB` e `Docker Compose` nessa POC?




# 🚨 SPOILER — Respostas








## 1. Projeto

- **Maven:** gerenciador de dependências e build do projeto.
- **Java:** linguagem utilizada.
- **JAR:** formato do arquivo compilado da aplicação.
- **YAML:** formato utilizado para configurações da aplicação.
- **Java 21:** versão do Java utilizada no projeto.

---

## 2. Estrutura

- **`.idea` / `.vscode`:** configurações pessoais da IDE.
- **`.mvn`:** configurações internas do Maven.
- **`.gitignore`:** arquivos que não devem ir para o Git.
- **`.gitattributes`:** define como o Git deve tratar diferentes arquivos.
- **`mvnw` / `mvnw.cmd`:** permitem usar o Maven sem instalá-lo globalmente.
- **`compose.yaml`:** configuração do Docker Compose.
- **`pom.xml`:** configurações, dependências e plugins do Maven.
- **`src/main`:** código principal da aplicação.
- **`src/test`:** testes da aplicação.

---

## 3. `pom.xml`

**a)** É o arquivo principal de configuração do Maven.

**b)**

```text
dependencies → bibliotecas utilizadas pela aplicação

plugins → ferramentas utilizadas pelo Maven
````

---

## 4. Dependências

- **Docker Compose Support:** suporte ao Docker Compose.
    
- **DevTools:** melhora a experiência de desenvolvimento.
    
- **Spring Web:** permite criar APIs.
    
- **Spring Security:** adiciona recursos de segurança.
    
- **Spring Data MongoDB:** facilita a integração com MongoDB.
    

---

## 5. Aplicação

O `main` é o ponto de entrada da aplicação.

`SpringApplication.run()` inicia a aplicação Spring Boot.

`@SpringBootApplication` indica que aquela é a classe principal da aplicação Spring Boot.

---

## 🚨 Pergunta final

Usamos **Spring Data MongoDB** para facilitar a integração da aplicação Spring com o MongoDB.

Usamos **Docker Compose** para configurar e executar o MongoDB localmente, evitando diferenças de ambiente.