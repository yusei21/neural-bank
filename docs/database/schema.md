# Database Schema

PostgreSQL é a fonte transacional de verdade do Neural Bank.

## Tabelas iniciais

### customers

- `id` UUID PK
- `name`
- `email` UNIQUE
- `created_at`

### accounts

- `id` UUID PK
- `customer_id` FK -> customers
- `balance` NUMERIC(19,2)
- `status`
- `created_at`

### transactions

- `id` UUID PK
- `account_id` FK -> accounts
- `type`
- `amount` NUMERIC(19,2)
- `description`
- `created_at`

O schema é versionado exclusivamente por migrations Flyway. `ddl-auto` permanece como `validate` para impedir alterações implícitas do Hibernate.
