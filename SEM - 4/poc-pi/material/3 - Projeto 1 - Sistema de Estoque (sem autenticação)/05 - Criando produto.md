# Resolução dos desafios do documento 4:

## Criando Repository:
```java
public interface ProductRepository extends MongoRepository<Product, UUID> {  
}
```

## Criando Service
```java
@Service // -> Seu primeiro Componente Criado! Poderia ser @Component também, mas não seria muito semântico.
public class ProductService {  
      
}
```

## Fazendo DI (Dependency Injection):
```java
@Service  
public class ProductService {  
    private final ProductRepository productRepository;  
    
    public ProductService(ProductRepository productRepository) {  
        this.productRepository = productRepository;  
    }
}
```



# Criação de DTOs (tipos de entradas e saídas)
Em `domains`, vamos criar uma pasta `dtos` para armazenar os `requests` e `responses`, criando uma pasta para `requests` e `responses` também.

Antes de começarmos a fazer a lógica, precisamos definir **O QUE VAMOS RECEBER**, para isso, vamos criar um `CreateProductDTO`, com alguns campos.

então em `domains/dtos/requests`, criamos um `record` chamado `CreateProductDTO`.


> Nota: Como o polígono ainda não passou Records (e nem sei se vai passar, pq ele tá usando java 11 se pá, e nessa época não existia records), recomendo estudarem  um pouco sobre. Mas imaginem como se fosse uma classe para tipos, onde os "atributos" ficam dentro dos parênteses.

então, a primeira vista, temos isso:
```java
public record CreateProductDTO() {  
}
```

E de acordo com o requisito de criação de produto (**RF01**) e com o modelo que a gente criou:

precisamos que o request venha com o seguinte body:
```json
{
	"name": "String",
	"description": "String",
	"price" 0.00,
	"quantity": 3
}
```

Então, o `CreateProductDTO` fica assim de primeiro momento:

```java
public record CreateProductDTO(  
        String name,  
        String description,  
        BigDecimal price,  
        Integer quantity  
) {  
}
```


# Desafio:

Você consegue implementar esse método no service usando sua lógica e o conhecimento adquirido em `4.1 - Spring Data`?:
```java
public Product create(CreateProductDTO dto){  
// TODO: Fazer a lógica para criar um produto.
}
```