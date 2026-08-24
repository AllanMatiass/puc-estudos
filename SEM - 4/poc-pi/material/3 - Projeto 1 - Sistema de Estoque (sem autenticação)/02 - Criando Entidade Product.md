Supondo que vocês já viram a seção "Configuração", podemos partir da criação de entidades. Caso contrário, veja a pasta "1 - Configuração" e "Spring Essencial/01 - Arquitetura em Camadas" antes de avançar.

Baseado no Item 2 (Escopo), podemos criar uma entidade Product em `domains/Product.java`, tendo algumas annotations que estão disponíveis em Spring Essencial (02 e 04):

```java
@Document  
public class Product {  
  
    @Id  
    private final UUID id;  
  
  
    private String name;  
    private String description;  
  
    // Por que BigDecimal? É mais preciso que os tipos primitivos como Float/Double por exemplo  
    private BigDecimal price;  
  
    private int totalStock;  
  
    private int purchased;  
  
  
    @CreatedDate // Pega o instante em que a instância foi criada automaticamente. 
    private LocalDateTime createdAt;  
    
}
```

# Desafio
Seguindo os princípios de POO e de Injeção de Dependências que a gente falou em `Spring Essencial`, você consegue:
- Fazer o required args constructor?
- Fazer o no args constructor?
- Fazer os Getters e Setters?

Tente fazer o desafio antes de avançar. Lembra de "Explorar" as classes, como UUID, BigDecimal, etc