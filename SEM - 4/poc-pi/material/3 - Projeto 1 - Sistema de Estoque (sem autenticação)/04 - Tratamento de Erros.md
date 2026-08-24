Antes de seguirmos para os requisitos, precisamos tratar os erros que lançamos durante nossa modelagem de `Product`.

Vou introduzir algumas novas annotations:

# @RestControllerAdvice
Diz ao Spring que aquela classe é responsável por pegar e tratar erros. Só pode ser usado em Classes.

# @ExceptionHandler
Deve ser usado em métodos das classes que estão anotadas com `@RestControllerAdvice`. Ele serve para pegar uma exception e tratar para retornar de forma amigável para o usuário.

# Como usar?

em `exceptions/AppExceptionHandler` vamos concentrar todos os tratamentos de erros.

Por enquanto, a classe final está assim:
```java
@RestControllerAdvice  
public class AppExceptionHandler {  
  
    @ExceptionHandler  
    public ResponseEntity<String> handleException(Exception e){  
        // por questões de segurança, não exibimos o que aconteceu quando o erro é 500  
        return ResponseEntity.internalServerError().body("Internal Server Error");  
    }  
    @ExceptionHandler  
    public ResponseEntity<String> handleException(IllegalArgumentException e){  
        return ResponseEntity.badRequest().body(e.getMessage());  
    }}
```

pois apenas temos 2 casos de erros: Quando lançamos um `IllegalArgumentException` ou quando acontece algum erro que ainda não sabemos.

Vou explicar o `ResponseEntity` mais a fundo quando estudarmos sobre **controllers**.
Por enquanto, saibam que ele pega o erro e retorna uma Resposta adequada para o usuário.

# Desafio:

Baseado no que vimos em Spring Essentials (1, 2 e 4):
- Você consegue criar um Repositório para a Entidade `Product` chamado `ProductRepository` em `repositories/`?
- Você consegue criar a classe `ProductService` corretamente? (não precisa implementar os métodos ainda)
- Você consegue fazer a Injeção de Dependência para que: `ProductService` precise de `ProductRepository`? 

Tente fazer antes de prosseguir com o documento 5.
