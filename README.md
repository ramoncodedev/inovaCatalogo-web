# CatalogoWeb

Backend da plataforma **CatalogoWeb** — catálogo digital e gestão.

- **Java 21** + **Spring Boot 4.1**
- **PostgreSQL 17** com migrations via **Flyway**
- **Swagger UI** (springdoc-openapi) para documentação e contrato com o frontend

---

## Pré-requisitos

Escolha um dos caminhos:

- **Via Docker (recomendado pro dev frontend):** só precisa de `Docker` + `Docker Compose`.
- **Dev local (backend):** `JDK 21` e `Docker` (só pra subir o Postgres).

---

## Setup

### 1. Clonar e configurar variáveis de ambientea

```bash
git clone <repo-url>
cd catalogoweb
cp .env.example .env
```

Edite `.env` se quiser trocar senha, porta ou nome do banco.

### 2a. Rodar tudo via Docker (backend + banco)

```bash
docker compose up --build
```

- App: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Postgres: `localhost:5443` (usuário/senha do `.env`)

Pra parar: `docker compose down`. Pra apagar o volume do banco: `docker compose down -v`.

### 2b. Rodar só o banco no Docker e o app no IDE / terminal

```bash
docker compose up -d postgres
./mvnw spring-boot:run
```

No Windows: `mvnw.cmd spring-boot:run`.

---

## Endpoints

Documentação completa e interativa:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

Resumo dos endpoints atuais:

| Método | Rota    | Descrição                                       |
|--------|---------|-------------------------------------------------|
| POST   | `/lead` | Cadastra um lead a partir do formulário público |

---

## Estrutura do projeto

```
src/main/java/com/inova/catalogoweb/
├── config/          # WebConfig, OpenApiConfig
├── exception/       # ApiError + ApiExceptionHandler global
├── lead/            # Entity, Repository, Service, Controller, DTOs
└── CatalogowebApplication.java

src/main/resources/
├── application.yaml
└── db/migration/    # Flyway (V1__..., V2__...)
```

---

## Migrations

O Flyway roda automaticamente na subida do app e valida contra o banco (`spring.jpa.hibernate.ddl-auto=validate`). Nova alteração de schema = novo arquivo em `src/main/resources/db/migration/V{n}__descricao.sql`. Nunca editar migration já aplicada em ambiente compartilhado.

---

## Variáveis de ambiente

Todas as configs sensíveis vêm do `.env` (ver `.env.example`):

| Variável                     | Descrição                                  |
|------------------------------|--------------------------------------------|
| `POSTGRES_DB`                | Nome do banco                              |
| `POSTGRES_USER`              | Usuário do Postgres                        |
| `POSTGRES_PASSWORD`          | Senha do Postgres                          |
| `POSTGRES_PORT`              | Porta exposta do Postgres no host (5443)   |
| `SPRING_DATASOURCE_URL`      | URL JDBC (usada só no dev local via mvnw)  |
| `SPRING_DATASOURCE_USERNAME` | Usuário JDBC                               |
| `SPRING_DATASOURCE_PASSWORD` | Senha JDBC                                 |
| `APP_PORT`                   | Porta exposta do app no host (default 8080)|

No `docker compose`, a URL do datasource é sobrescrita para apontar pro serviço `postgres` da rede interna do compose.

---

## Fluxo de trabalho

- Backend (Spring Boot) e frontend consomem o mesmo contrato via Swagger.
- Alterações de API devem manter o Swagger atualizado (as anotações `@Operation` / `@ApiResponse` são o contrato).