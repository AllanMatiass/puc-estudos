
# O que é Injeção de Dependências?

**Injeção de Dependências (Dependency Injection — DI)** é um padrão onde uma classe não cria diretamente as dependências que precisa. Em vez disso, elas são fornecidas para ela.

## Exemplo
### Sem DI
```java
public class UserService {

    private UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }
}
```

Aqui `UserService` está fortemente acoplado a `UserRepository`.

Se amanhã quisermos trocar `UserRepository` por `MongoUserRepository`, precisamos alterar o `UserService`

### Com DI

```java
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Agora `UserService` **não sabe como o repository é criado**. Quem usa a classe fornece a dependência via construtor.
Isso reduz o acoplamento e facilita testes.

# E onde o Spring entra nisso?
O Spring possui um **Container de Inversão de Controle (IoC Container)**.
Você registra objetos no Spring e ele passa a controlar a criação e o ciclo de vida deles.

Por exemplo:
```ruby
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

O Spring percebe o `@Service` e cria um objeto `UserService`.  Depois, ele percebe que precisa de um `UserRepository` e procura um objeto desse tipo no container.

Encontrando, ele faz a injeção:

```ruby
Spring Container

UserRepository
      ↓
      ↓ injeta
      ↓
UserService
```


# Por que usar `private final`?

É muito comum encontrar:

```java
private final UserRepository repository;
```

Isso significa que a dependência:
- precisa existir;
- é obrigatória;
- não pode ser substituída depois da construção do objeto.

Por isso, normalmente usamos **injeção pelo construtor**.
```java
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```
Essa é geralmente a forma mais recomendada de DI.

# Annotations mais Comuns
### @Service

`@Service` é uma especialização de `@Component`.

```java
@Service
public class UserService {
}
```

Conceitualmente:

```java
@Component
   │
   ├── @Service
   ├── @Repository
   └── @Controller
```

A diferença é principalmente **semântica**.

`@Service` indica:

> "Essa classe contém lógica de negócio."

Exemplo:

```java
@Service
public class PaymentService {

    public void processPayment() {
        // regra de negócio
    }
}
```

### @Repository
Indica uma classe responsável pelo acesso a dados.

```java
@Repository
public class UserRepository {
}
```

Normalmente fica na camada de persistência:

```ruby
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

No **Spring Data**, muitas vezes você nem precisa escrever a implementação:

```java
public interface UserRepository
        extends JpaRepository<User, UUID> {
}
```
O Spring cria a implementação automaticamente. A gente vai aprofundar mais na prática.

### @Controller
Usado principalmente para controllers MVC.

```java
@Controller
public class UserController {
}
```

Quando estamos construindo uma API REST, normalmente usamos `@RestController`. Que é a Annotation a seguir.

### @RestController
É uma das annotations mais importantes para APIs REST.

```java
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService service;
	
    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }
    
    // required args constructor (construtor com DI)
}
```

Basicamente:
```ts
@RestController
      =
@Controller + @ResponseBody
```
Ou seja, o retorno dos métodos normalmente é transformado em JSON.

### !!!!!!!!! @Bean !!!!!!!!!

Aqui está uma diferença importante.

`@Component` é colocado **na classe**:

```java
@Component
public class EmailService {
}
```

Já `@Bean` é colocado **em um método**:

```java
@Configuration
public class AppConfig {

    @Bean
    public EmailService emailService() {
        return new EmailService();
    }
}
```

O Spring pega o objeto retornado:

```java
return new EmailService();
```

e coloca no container.

Depois podemos fazer a injeção de dependência:

```java
@Service
public class UserService {

    private final EmailService emailService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

### Então qual a diferença entre `@Component` e `@Bean`?
##### `@Component`
Você controla a classe:

```java
@Component
public class EmailService {
}
```

O Spring descobre a classe através do **component scanning**.
##### `@Bean`
Você quer controlar explicitamente a criação:

```java
@Configuration
public class Config {

    @Bean
    public EmailService emailService() {
        return new EmailService();
    }
}
```
Isso é especialmente útil para classes de bibliotecas externas.

Por exemplo:

```java
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

Você não pode simplesmente colocar:

```java
@Component
public class ObjectMapper
```

porque `ObjectMapper` é uma classe externa.

Então usamos `@Bean` para o Spring fazer a inversão de controle corretamente.

### @Configuration
Indica uma classe que contém configurações do Spring.

```java
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

É muito comum aparecer junto com `@Bean`.

```ts
@Configuration
       ↓
	@Bean
	@Bean
	@Bean
```

### @SpringBootApplication
É uma das annotations mais importantes do Spring Boot.

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Ela combina principalmente:

```ts
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

### @ComponentScan
Procura classes como:
```ts
@Component
@Service
@Repository
@Controller
@RestController
```
e registra essas classes no **container de inversão de controle**.


