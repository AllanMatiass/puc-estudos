Antes de começar a codar, precisamos estabelecer a conexão do mongo com a nossa aplicação.

então, vamos á pasta: `src/main/resources/application.yaml` e configurar a conexão com o mongo local e colocamos:

uri: mongodb://username:senha@localhost:porta/databaseName?authSource=admin
uri: mongodb://root:secret@localhost:27017/usersManager?authSource=admin`

##### as credenciais devem ser a mesma que estiver no docker compose

e no final, o arquivo de configuração ficou assim:

```yaml
spring:
  application:
    name: pi

  mongodb:
    uri: mongodb://root:secret@localhost:27017/usersManager?authSource=admin
```

# rodando a aplicação

para rodar a aplicação, primeiro rodamos o arquivo `PiApplication.java`
(assumindo que você tenha o docker instalado na sua máquina)