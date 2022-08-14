#  Projeto yarn


### Criar Projeto Yarn para o Auth-Api e Vendas-api
```
yarn init -y
```

### Adicionar Dependências
```
yarn add express
```

```
yarn add cors nodemon -D
```

```
yarn add jsonwebtoken
```

```
yarn add mongoose
```

```
yarn add sequelize
```

```
yarn add pg
```

```
yarn add bcrypt
```

OBS: caso ocorra um erro de Execution_Policies desabilitada é necessário abrir o PoweShell(como administrador) e executar o comando

```
Set-ExecutionPolicy Unrestricted
```

### Iniciar aplicação node 
```
yarn startDev
```

```
yarn startContainer
```



#  Docker 


## Auth-api

### Gerar o build da imagem docker
```
docker image build -t auth-api:1.0 .
```

### Executar a imagem docker

```
docker run --name auth-api -e PORT=8080 -p 8080:8080 auth-api:1.0
```

## Vendas-api

### Gerar o build da imagem docker
```
docker image build -t vendas-api:1.0 .
```

### Executar a imagem docker

```
docker run --name vendas-api -e PORT=8082 -p 8082:8082 vendas-api:1.0
```



## Produto-api

### Gerar o build da imagem docker
```
docker image build -t produto-api:1.0 .
```

### Executar a imagem docker

```
docker run --name produto-api -e PORT=8081 -p 8081:8081 produto-api:1.0
```


# Docker compose

### Para iniciar toda a infraestrutura em um único comando é necessário utilizar o docker compose:


```
docker-compose up --build
```

# Hospedagem

### Heroku: https://dashboard.heroku.com/apps
### Mongo Cloud: https://cloud.mongodb.com/v2/62f890f854f18a7fc5a400c1#setup/access?includeToast=true
### CloudAMQP-RabbitMQ: https://www.cloudamqp.com/