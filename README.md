#  Projeto yarn

## Auth-api

### Criar Projeto Yarn
```
yarn init -y
```

### Abrir VsCode
```
code .
```

### Adicionar Dependências
```
yarn add express
```

```
yarn add cors nodemon -D
```

OBS: caso ocorra um erro de Execution_Policies desabilitada é necessário abrir o PoweShell(como administrador) e executar o comando

```
Set-ExecutionPolicy Unrestricted
```

## Vendas-api

### Iniciar aplicação node 
```
yarn startDev
```

### Adicionar Json Web Token
```
yarn add jsonwebtoken
```

### Adicionar Mongose
```
yarn add mongoose
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
