# AI Architecture

A camada de IA será adicionada somente após o core bancário estar confiável.

## Primeira etapa

Um único Financial Assistant com ferramentas somente de leitura:

- `getAccount`
- `getBalance`
- `getTransactions`

## Regra de segurança

O modelo nunca acessa diretamente repositories, PostgreSQL ou SQL.

```text
LLM
  -> Tool
    -> Application Service
      -> Domain
        -> Repository
```

## Evolução planejada

1. Chat simples.
2. Tool calling somente leitura.
3. Análise e categorização de transações.
4. Memória e RAG quando houver necessidade concreta.
5. Operações mutáveis com confirmação humana obrigatória.
6. Experimentos com agentes especializados.
7. Multi-agent e MCP apenas em fases avançadas.

Os experimentos devem ser documentados em `docs/ai/experiments/` antes de serem promovidos para a arquitetura principal.
