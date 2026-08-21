# Ambiente
Para melhor entendimento e melhor DX (developer experience), vou usar o **IntelliJ**, mas podem usar o **VSCode** ou qualquer outra IDE de sua preferência.

# Estrutura de Pastas
![[estrutura de pastas.png]]

Antes de qualquer coisa, **NO INTELLIJ**, cada "." é uma subpasta. Por exemplo: `com.poc.pi`, são 3 pastas diferentes.

### Pastas
##### .idea ou .vscode:
configuração de ambiente pessoal, não deve ir pro github.

##### .mvn:
Maven, configurações internas do maven, deve ir pro github para não dar conflito entre ambientes.

**.gitattributes**:
é um arquivo que define **como o Git deve tratar diferentes tipos de arquivos no repositório**, como quebra de linha e arquivos binários.

**.gitignore**: lá contém os arquivos que não devem ir para o repositório (arquivos de credenciais, build, configurações de ambientes, etc)

**mvnw/mvnw.cmd**: um maven do ambiente, ou seja, você **NÃO PRECISA INSTALAR O MAVEN NA SUA MÁQUINA** para compilar, testar o projeto, etc.

**compose.yaml**: configurações do docker compose, para falar disso, precisa de um curso inteiro, então por hora, não foque em decorar a sintaxe, mas entender que ele é o responsável por subir as aplicações para não dar conflito entre ambientes.

**pom.xml**:
	Arquivo de dependências e plugins. Semelhante ao `package.json`em ambientes javascript. O próximo documento vai ser explicando um pouco sobre ele.


## 🚨 IMPORTANTE — Pasta `src`

Agora que entendemos um pouco da estrutura geral do projeto, podemos partir para a pasta em que vocês vão mexer em 80% do tempo.

##### pasta main:
lá vai conter 2 partes importantes para qualquer projeto:

java: código fonte e package principal (`com.doc.pi`)
resources/application.yaml (ou .properties): configurações de ambiente, senhas de bancos, connection strings, etc. Possui integração com variáveis de ambiente e podem ter multiplos **profiles**, mas para **ESSA POC**, vamos usar apenas 1 profile para evitar complexidade.

java/com/poc/pi/PiApplication (ou o nome do artefato, nesse caso é pi):
terá uma estrutura padrão, e que creio que para esse projeto não precisaríamos mudar nada, mas caso haja necessidade de fazer algo ANTES da aplicação rodar, deve ir na main antes da chamada do método `run`, em seu estado inicial é parecido com isso:

```java
@SpringBootApplication
public class PiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiApplication.class, args);
	}

}

```

##### pasta test/java:
lá vai conter os testes da aplicação, podemos usar o JUnit para os testes, conforme vai ser ensinado. E vamos aprender sobre testes conforme o avanço dessa POC


> 	Os arquivos e pastas DO PROJETO (services, controllers, repositories, etc), devem ser filhos do artefato, nesse caso: (`pi`)


