![[arquitetura.png]]

# Responsabilidades de cada pasta
- `config` é a pasta onde vamos mexer com configurações, lá vamos aprender sobre `@Bean` e filters
- `controllers` a "porta" da aplicação, onde as requisições vão chegar, lá vamos aprender sobre `@RestController`, `@Get/Post/Put/DeleteMapping`, `@PathVariable`, `@RequestBody`, `@PathVariable`, `ResponseEntity<T>`, Injeção de dependências e outros conceitos importantes
- `domain` fica os modelos, enums, tipos de classe, etc.
- `exceptions` serve pra gente configurar o tratamento de erros. Vamos usar uma estratégia que captura o erro e lança uma response padrão.
- `repositories` aqui vai ficar a comunicação com o banco,  lá, criamos interfaces com métodos para manipular uma entidade.
- `services` aqui vamos ver sobre `@Service`, aprofundar em injeção de dependência e aqui vai ficar as regras de negócio que vão ser usadas pelos controllers.





