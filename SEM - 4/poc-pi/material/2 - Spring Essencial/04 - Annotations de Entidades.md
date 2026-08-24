Nesse documento, terá uma série de Annotations comuns que você verá em entidades durante boa parte do tempo enquanto utiliza Spring.
### @Entity / @Document

Usada no JPA para indicar uma entidade persistida no banco. Vai depender de qual Spring Data você estará usando, mas o propósito é o mesmo. Se estiver usando um banco relacional, será `@Entity`, se for não relacional, será `@Document`


```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
```

### @Id
Define a chave primária ou id:

```java
@Id
private UUID id;
```

### @GeneratedValue
Indica que o valor pode ser gerado automaticamente e aceita parâmetros. Exemplo:

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

Com **MongoDB**, normalmente você não usa `@GeneratedValue`, porque MongoDB trabalha naturalmente com `ObjectId` ou pode usar UUID/string como `_id`.

Com Spring Data MongoDB, por exemplo:

```java
@Document
public class User {

    @Id
    private UUID id;

    private String name;
}
```

Você pode usar UUID, mas **quem vai gerenciar a geração do ID é o Spring Data/Mongo ou sua própria aplicação**, não o `GenerationType.UUID`.

