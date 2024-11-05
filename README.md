# Sistema de Votação de Pautas por Associados

API para gerenciar sessões de votações de pautas, em um ambiente de cooperativismo.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Mysql](https://dev.mysql.com/downloads/)
- [Kafka](https://bitnami.com/stack/kafka/containers)
- [Docker](https://www.docker.com/)

## Práticas adotadas

- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro

## Dependências

Para executar a API, é necessário que o Docker esteja instalado na máquina, para que seja possível gerar os containers serão executadas as instâncias do MySQL e do Kafka, conforme configurado no arquivo docker-compose.yaml.

## Como Executar

- Clonar repositório git
```
$ git clone https://github.com/matheuskrol/assembleia-votacao-associados.git
```
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/assembleia-votacao-associados-0.0.1-SNAPSHOT.jar
```

A URL de acesso da API poderá ser acessada em [localhost:8080](http://localhost:8080).

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/):

- Inserir Associado
```
POST http://localhost:8080/associados/inserir

body:
{
    "nome": "Associado",
    "cpf": "22338940814"
}

responseBody:
{
    "id": 1,
    "nome": "Associado",
    "cpf": "22338940814"
}

statusCode: 201
```
- Alterar Associado
```
PUT http://localhost:8080/associados/alterar/1

body:
{
    "nome": "Associado 2",
    "cpf": "22338940814"
}

responseBody:
{
    "id": 1,
    "nome": "Associado 2",
    "cpf": "22338940814"
}

statusCode: 202
```
- Listar Associados
```
GET http://localhost:8080/associados/listar

responseBody:
[
    {
        "id": 1,
        "nome": "Associado 2",
        "cpf": "22338940814"
    }
]

statusCode: 200
```
- Inserir Pauta
```
POST http://localhost:8080/pautas/inserir

body:
{
    "descricao": "Pauta"
}

responseBody:
{
    "id": 1,
    "descricao": "Pauta",
    "status": "PENDENTE",
    "totalVotos": 0,
    "votosFavoraveis": 0,
    "votosContra": 0
}

statusCode: 201
```
- Listar Pautas
```
GET http://localhost:8080/pautas/listar

responseBody:
[
    {
        "id": 1,
        "descricao": "Pauta",
        "status": "PENDENTE",
        "totalVotos": 0,
        "votosFavoraveis": 0,
        "votosContra": 0
    }
]

statusCode: 200
```
- Abrir Sessão
```
POST http://localhost:8080/sessoes/abrirSessao

body:
{
    "idPauta": 1,
    "segundosAbertura": 90
}

responseBody:
{
    "id": 1,
    "pauta": null,
    "idPauta": 1,
    "horarioInicio": "2024-11-04T12:34:05.8066247",
    "horarioTermino": "2024-11-04T12:35:05.8066247"
}

statusCode: 200
```
- Registrar Voto
```
POST http://localhost:8080/votos/registrarVoto

body:
{
    "idPauta": 1,
    "segundosAbertura": 90
}

responseBody:
{
    "id": 1,
    "pauta": null,
    "idPauta": 1,
    "horarioInicio": "2024-11-04T12:34:05.8066247",
    "horarioTermino": "2024-11-04T12:35:05.8066247"
}

statusCode: 200
```
