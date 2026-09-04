# Docker local environment

O PostgreSQL local é executado via Docker Compose.

## Subir o banco

```bash
docker compose up -d postgres
```

## Verificar os containers

```bash
docker compose ps
```

## Parar o ambiente

```bash
docker compose down
```

## Remover também os dados locais

```bash
docker compose down -v
```

Por padrão, a aplicação usa:

- database: `neural_bank`
- user: `neural_bank`
- password: `neural_bank`
- port: `5432`

Esses valores são apenas para desenvolvimento local e podem ser sobrescritos pelas variáveis descritas em `.env.example`.
