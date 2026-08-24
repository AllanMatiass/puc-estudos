Nesse documento, terá uma série de Annotations comuns que você verá em controllers durante boa parte do tempo enquanto utiliza Spring.
### @RequestMapping
Define uma rota.

```java
@RestController
@RequestMapping("/users")
public class UserController {
}
```

Agora o controller trabalha com:

```ts
/users
```

E `@RequestMapping` também pode ser usado em métodos.

### @(Método HTTP)Mapping
Nessa seção, vou resumir, mas basicamente temos os principais métodos HTTP, que vocês já conhecem de cabo a rabo (Get, Post, Put, Patch, Delete).

```java
@GetMapping("/users")
public List<User> findAll() {
    return service.findAll();
}
```

Equivale conceitualmente a:

```java
@RequestMapping(
    method = RequestMethod.GET,
    path = "/users"
)
```

### @PathVariable
Pega uma variável da URL.

```java
@GetMapping("/users/{id}")
public User findById(@PathVariable UUID id) {
    return service.findById(id);
}
```

Requisição:

```ruby
GET /users/42
```

Então:

```
id = 42
```

### @RequestParam
Pega parâmetros da URL.

Pega parâmetros da URL.

```java
@GetMapping("/users")
public List<User> find(
        @RequestParam String name
) {
    return service.findByName(name);
}
```

Requisição:

```ruby
GET /users?name=Cristian
```

Então:

```
name = "Cristian"
```

### @RequestBody
Pega o corpo da requisição e transforma em um objeto Java.

Request:
```json
{
    "name": "Matias",
    "age": 19
}
```

Controller:

```java
@PostMapping
public User create(@RequestBody CreateUserDTO user) {
    return service.create(user);
}
```

O Spring utiliza o Jackson para fazer a conversão:

```ruby
JSON
 ↓
Jackson (intermediário para "transformar" o json puro em UserDTO)
 ↓
UserDTO
```

