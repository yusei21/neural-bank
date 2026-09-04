# Neural Bank

Backend bancário para estudo de engenharia de software com Spring, evoluindo gradualmente para experimentos com agentes de IA.

O projeto começa como um **monólito modular**, usando **POO como base do domínio** e reservando **POA para preocupações transversais** como auditoria, tracing e métricas.

## Stack inicial

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Bean Validation
- JUnit 5
- Testcontainers
- Docker Compose
- Maven

## Domínio atual

A `v0.1.0` inicia com três módulos:

```text
customer/
account/
transaction/
```

Operações implementadas:

- criação de cliente;
- criação e consulta de conta;
- depósito;
- saque;
- transferência atômica;
- histórico de transações.

## Executando localmente

Requisitos:

- Java 21+
- Maven
- Docker + Docker Compose

Suba o PostgreSQL:

```bash
docker compose up -d postgres
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080/api/v1`.

Para remover banco e volume local:

```bash
docker compose down -v
```

## Endpoints iniciais

```text
POST /api/v1/customers
POST /api/v1/accounts
GET  /api/v1/accounts/{accountId}
POST /api/v1/accounts/{accountId}/deposit
POST /api/v1/accounts/{accountId}/withdraw
POST /api/v1/transfers
GET  /api/v1/accounts/{accountId}/transactions
```

## Documentação

A documentação técnica fica versionada em [`docs/`](docs/README.md).

```text
docs/
├── architecture/
│   ├── overview.md
│   └── decisions/
├── ai/
│   └── overview.md
├── database/
│   └── schema.md
├── getting-started/
│   └── docker.md
└── roadmap/
    └── roadmap.md
```

## IA

A camada de IA será introduzida depois do core bancário.

A primeira versão terá um único assistente financeiro com ferramentas **somente de leitura**. O modelo não terá acesso direto ao PostgreSQL nem aos repositories:

```text
LLM
  -> Tool
    -> Application Service
      -> Domain
        -> Repository
```

RAG, memória, MCP e multi-agentes serão experimentados em fases posteriores, apenas quando houver casos de uso que justifiquem a complexidade.

Veja [`docs/ai/overview.md`](docs/ai/overview.md) e [`docs/roadmap/roadmap.md`](docs/roadmap/roadmap.md).
