# O conceito mais importante

Tente pensar no Spring assim:

```
                 SPRING CONTAINER
                       │
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
 UserRepository   EmailService   PasswordEncoder
        │              │              │
        └──────────────┼──────────────┘
                       ↓
                  UserService
                       ↓
                  UserController
```

O Spring fica responsável por **montar os objetos e conectar suas dependências**.

Você declara:

> "UserService precisa de UserRepository."

E não:

> "UserService precisa criar um UserRepository."

Essa diferença é justamente a essência da **Inversão de Controle + Injeção de Dependências**.

# As annotations que você mais precisa decorar

| Annotation                 | Para que serve                                 |
| -------------------------- | ---------------------------------------------- |
| `@SpringBootApplication`   | Inicializa a aplicação Spring Boot             |
| `@Component`               | Registra uma classe como Bean                  |
| `@Service`                 | Bean da camada de negócio                      |
| `@Repository`              | Bean da camada de persistência                 |
| `@RestController`          | Controller REST                                |
| `@Controller`              | Controller MVC                                 |
| `@Configuration`           | Classe de configuração                         |
| `@Bean`                    | Registra manualmente um objeto no container    |
| `@Autowired`               | Injeta dependências                            |
| `@Qualifier`               | Escolhe qual Bean injetar                      |
| `@Primary`                 | Define Bean padrão                             |
| `@GetMapping`              | Endpoint GET                                   |
| `@PostMapping`             | Endpoint POST                                  |
| `@PutMapping`              | Endpoint PUT                                   |
| `@DeleteMapping`           | Endpoint DELETE                                |
| `@RequestBody`             | Obtém JSON do body                             |
| `@PathVariable`            | Obtém variável da URL                          |
| `@RequestParam`            | Obtém query parameter                          |
| `@Transactional`           | Controla transações                            |
| `@Entity` / `@Document`    | Define entidade JPA / Documento Não Relacional |
| `@Id`                      | Define chave primária                          |
| `@Value`                   | Injeta configuração                            |
| `@ConfigurationProperties` | Mapeia configurações                           |
| `@ConditionalOnProperty`   | Instancia o Bean de acordo com a configuração  |
