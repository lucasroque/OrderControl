# Projeto de Controle de Pedidos

## Tecnologias Utilizadas

* Java 21
* Spring Boot 3.3.5
* Hibernate
* Supabase (PostgreSQL)

## Camadas do Projeto

* Controller: Reponsável por receber e responder as requisições HTTP.
* Service: Contém a lógica de negócios.
* Repository: Interface para a camada de persistência.
* Entity: Representa as tabelas do banco de dados.
* DTO: Usado para tranferência de dados entre o cliente e a aplicação.

## Funcionalidades

### Criar Pedido

* Rota:
```http
POST /api/order HTTP/1.1
Content-Type: application/json
```
* Exemplo de body:
```json
{
  "client": "Luke Skywalker",
  "date": "2024-11-07",
  "products": [
    { "name": "Mão Robótica", "value": 999.0 },
    { "name": "Droid", "value": 2999.0 }
  ]
}
```

### Consultar Pedidos

* Rota:
```http
GET /api/orders HTTP/1.1
Content-Type: application/json
