# E-commerce API — Java & Spring Boot

API REST para o domínio de uma loja virtual, com cadastro de produtos e gerenciamento de pedidos.

**Tecnologias:** Java 21 · Spring Boot 3 · Spring Data JPA · H2 · Maven  
**Status:** Projeto de portfólio / aprendizado de back-end.

## Funcionalidades
- Cadastro, consulta, edição, busca e exclusão de produtos.
- Criação e consulta de pedidos.
- Atualização de status e cancelamento de pedidos.
- Uso de DTOs, serviços e repositórios para separar responsabilidades.

## Endpoints principais

| Método | Rota | Ação |
|---|---|---|
| `GET` | `/api/products` | Lista produtos |
| `GET` | `/api/products/search?name=...` | Pesquisa por nome |
| `POST` | `/api/products` | Cria produto |
| `PUT` | `/api/products/{id}` | Edita produto |
| `DELETE` | `/api/products/{id}` | Exclui produto |
| `GET` | `/api/orders` | Lista pedidos |
| `POST` | `/api/orders` | Cria pedido |
| `PATCH` | `/api/orders/{id}/status?status=...` | Altera status |
| `POST` | `/api/orders/{id}/cancel` | Cancela pedido |

## Executar localmente

Requisitos: JDK 21 e configuração do banco em `src/main/resources/application.properties`.

```bash
git clone https://github.com/fgscarvalho37-a11y/E-commerce-API.git
cd E-commerce-API
./mvnw spring-boot:run
```

No Windows: `mvnw.cmd spring-boot:run`.

## Arquitetura

A aplicação está organizada em `controller`, `service`, `repository`, `model` e `dto`. O objetivo é praticar APIs REST e regras básicas do processo de venda.

> Implementação educacional. Não inclui, por si só, checkout integrado nem operação de e-commerce em produção.

Desenvolvido por [Felipe Gomes](https://github.com/fgscarvalho37-a11y).