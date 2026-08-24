# Projeto: Sistema de Estoque

## 1. Objetivo

Desenvolver uma API REST para gerenciamento de produtos e estoque, utilizando **Java + Spring Boot**.

O sistema deve permitir cadastrar produtos, consultar produtos e controlar a entrada e saída de estoque, aplicando regras simples de negócio.

---

# 2. Escopo

O sistema terá apenas o módulo de **Produtos e Estoque**.
### Produto

Cada produto deverá possuir:

- `id`
- `name`
- `description`
- `quantity`
- `createdAt`

Exemplo:

```json
{
  "name": "Teclado Mecânico",
  "description": "Teclado mecânico ABNT2",
  "price": 299.90,
  "quantity": 10
}
```

---

# 3. Requisitos Funcionais

### RF01 — Cadastrar produto

O sistema deve permitir cadastrar um novo produto.

```
POST /products
```

Não deve ser possível cadastrar um produto com:

- nome vazio;
- preço menor ou igual a zero;
- quantidade negativa.

---

### RF02 — Listar produtos

O sistema deve permitir consultar todos os produtos.

```
GET /products
```

---

### RF03 — Buscar produto

O sistema deve permitir buscar um produto pelo ID.

```
GET /products/{id}
```

Caso o produto não exista, deve retornar `404 Not Found`.

---

### RF04 — Atualizar produto

O sistema deve permitir alterar os dados de um produto.

```http
PUT /products/{id}
```

Pode alterar:

- nome;
- descrição;
- preço.

A quantidade **não deve ser alterada diretamente** por esse endpoint.

---

### RF05 — Entrada de estoque

O sistema deve permitir adicionar unidades ao estoque.

```
PATCH /products/{id}/stock/entry
```

Exemplo:

```json
{
  "quantity": 10
}
```

Se o produto possuía:

```json
Estoque: 5
Entrada: 10
```

deve passar para:

```json
Estoque: 15
```

---

### RF06 — Saída de estoque

O sistema deve permitir retirar unidades do estoque.

```
PATCH /products/{id}/stock/exit
```

Exemplo:

```
{
  "quantity": 3
}
```

O sistema **não pode permitir estoque negativo**.

Se houver:

```
Estoque: 2
Saída: 3
```

deve retornar um erro.

---

### RF07 — Excluir produto

O sistema deve permitir excluir um produto.

```
DELETE /products/{id}
```

---

# 4. Regras de negócio

As principais regras são:

1. O nome do produto é obrigatório.
2. O preço deve ser maior que `0`.
3. A quantidade nunca pode ser negativa.
4. A entrada de estoque deve receber uma quantidade maior que `0`.
5. A saída de estoque deve receber uma quantidade maior que `0`.
6. Não é possível retirar mais produtos do que existem em estoque.
7. Produto inexistente deve gerar erro `404`.
8. A quantidade do produto deve ser alterada **somente através das operações de estoque**.

---

# 5. Arquitetura esperada

A aplicação deve seguir uma separação simples de responsabilidades:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Responsável por:

- receber requisições HTTP;
- validar entrada básica;
- chamar o Service;
- retornar respostas.

### Service

Responsável por:

- regras de negócio;
- entrada e saída de estoque;
- validações relacionadas ao domínio.

### Repository

Responsável pelo acesso ao banco.

---

# 6. Annotations que devem aparecer

A ideia é usar o projeto para praticar as annotations apresentadas.

### Spring

```
@SpringBootApplication
@RestController
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@PatchMapping
@DeleteMapping

@Service
@Repository
@Configuration
@Bean
```

### DI

Preferencialmente através de **injeção por construtor**:

```
public ProductService(ProductRepository repository) {
    this.repository = repository;
}
```

---

### Spring Data

```
@Document
@Id
```

---

# 7. Desafio opcional

Depois que o CRUD estiver funcionando, adicionar uma estratégia de desconto.

Criar:

```

public interface DiscountStrategy {

    BigDecimal calculate(Product product);
}
```

E duas implementações:

```
DiscountStrategy
       │
       ├── NoDiscount
       │
       └── WeekendDiscount
```

O `application.yml` deverá determinar qual estratégia será utilizada:

```
discount:
  provider: weekend
```

Utilizar:

```
@ConditionalOnProperty
```

para selecionar automaticamente a implementação.

---

# 9. O que NÃO faz parte do escopo

Para manter o projeto pequeno, não será necessário implementar:

- autenticação;
- JWT;
- cadastro de usuários;
- frontend;
- Redis;
- mensageria;
- microsserviços;
- AWS/Azure;
- paginação;
- sistema de permissões;
- histórico de movimentações.

Essas funcionalidades podem ser adicionadas posteriormente, mas **não fazem parte da primeira versão**.

---

# 10. Critério de conclusão

O projeto será considerado concluído quando for possível:

```
Cadastrar produto
       ↓
Consultar produto
       ↓
Atualizar produto
       ↓
Adicionar estoque
       ↓
Remover estoque
       ↓
Impedir estoque negativo
       ↓
Excluir produto
```

E a aplicação estiver organizada em:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

**A ideia é que o projeto seja pequeno o suficiente para terminar em uma sessão, mas tenha lógica suficiente para mostrar por que DI, Services, Repositories e regras de negócio existem.**