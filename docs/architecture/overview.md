# Architecture Overview

O Neural Bank começa como um monólito modular em Spring Boot.

## Princípios

- POO como base para regras e invariantes do domínio.
- POA apenas para preocupações transversais, como auditoria, tracing e métricas.
- Organização por feature: `customer`, `account`, `transaction` e futuramente `ai`.
- PostgreSQL como fonte transacional de verdade.
- Flyway para evolução explícita do schema.
- IA sem acesso direto ao banco de dados; ferramentas devem chamar application services.

## Módulos iniciais

```text
customer/
account/
transaction/
```

Cada módulo pode evoluir com `domain`, `application`, `infrastructure` e `web` conforme a necessidade.

## Evolução

A aplicação deve permanecer simples no início. Redis, filas, microserviços, RAG, MCP e multi-agentes só entram quando houver um problema concreto que justifique sua adoção.
