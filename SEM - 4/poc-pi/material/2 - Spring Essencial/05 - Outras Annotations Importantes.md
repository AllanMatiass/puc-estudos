Nesse documento, terá uma série de Annotations que também são importantes e podem ser usados em quase todo lugar enquanto utiliza Spring.

### @Transactional
Muito importante quando trabalhamos com banco de dados.

```java
@Transactional
public void transferMoney() {

    accountRepository.withdraw(...);

    accountRepository.deposit(...);
}
```

A ideia é que as operações façam parte de uma **transação**.

Se ocorrer um erro:

```ruby
withdraw
   ↓
deposit
   ↓
ERRO
```

o Spring pode fazer **rollback** das operações transacionais.

### @Value

Permite pegar valores de configuração.

`application.yml`:

```yaml
app:
  name: MyApplication
```

Java:

```java
@Value("${app.name}")
private String appName;
```

### @ConfigurationProperties
Para configurações maiores, costuma ser uma opção melhor que vários `@Value`

```yml
app:
  name: MyApplication
  version: 1.0
  timeout: 5000
```

Classe:

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;
    private int timeout;
}
```

Isso organiza melhor configurações relacionadas.

### @Profile
Permite configurar beans diferentes dependendo do ambiente.

Por exemplo:
```java
@Profile("dev")
@Bean
public PaymentService devPaymentService() {
    return new FakePaymentService();
}
```

E:

```java
@Profile("prod")
@Bean
public PaymentService prodPaymentService() {
    return new StripePaymentService();
}
```

Assim, ele muda a instância de acordo com o ambiente. Nesse caso:

```ruby
dev  → FakePaymentService
prod → StripePaymentService
```

### @Qualifier

Imagine que existem duas implementações:

```java
@Component
public class PixPayment implements Payment {
}
```

```java
@Component
public class CreditCardPayment implements Payment {
}
```

Se fizermos:

```java
public PaymentService(Payment payment) {
    ...
}
```

o Spring não sabe qual `Payment` utilizar.

Podemos usar:

```java
public PaymentService(
        @Qualifier("pixPayment") Payment payment
) {
    ...
}
```

### @Primary
Outra solução é definir uma implementação como padrão:

```java
@Component
@Primary
public class PixPayment implements Payment {
}
```

Agora, quando o Spring encontrar:

```java
Payment payment
```

ele prefere:

```java
PixPayment
```

### ConditionalOnProperty
imagine que temos o seguinte trecho em `application.yml`:

```yml
cloud:
  provider: aws
```

E então, declaramos uma instância baseado na configuração:

```java
@Bean
@ConditionalOnProperty(
    name = "cloud.provider",
    havingValue = "aws"
)
public CloudService awsCloudService() {
	// essa vai ser a instância se o cloud.provider for "aws"
    return new AwsCloudService();
}
```

E outro Bean:

```java
@Bean
@ConditionalOnProperty(
    name = "cloud.provider",
    havingValue = "azure"
)
public CloudService azureCloudService() {
	// essa vai ser a instância se o cloud.provider for "azure"
    return new AzureCloudService();
}
```


### Resumo de implementação de Bean

```ts
@Qualifier
→ "Qual Bean eu quero?"

@ConditionalOnProperty
→ "Esse Bean deve existir?"

@Profile
→ "Esse Bean deve existir neste ambiente?"

@Primary
→ "Se houver vários, qual é o padrão?"
```

# `@Bean` + DI na prática

Um exemplo completo:

```java
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Depois:

```java
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
```

O fluxo é:

```ruby
@Configuration
     │
     └── @Bean
           │
           ↓
PasswordEncoder
           │
           │
           ↓
      Spring IoC
           │
           │ injeta
           ↓
      UserService
```

Você não precisa fazer:

```java
new BCryptPasswordEncoder()
```

dentro do `UserService`.

