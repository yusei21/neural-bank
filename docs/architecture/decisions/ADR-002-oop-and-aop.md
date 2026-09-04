# ADR-002: POO como base e POA seletiva

## Status

Accepted.

## Decision

Usar Programação Orientada a Objetos para modelar domínio, regras e invariantes. Usar Programação Orientada a Aspectos apenas para preocupações transversais.

## POO

- saldo e movimentações;
- regras de conta;
- transferências;
- políticas do domínio;
- casos de uso.

## POA

- auditoria;
- logging e tracing;
- métricas;
- observabilidade;
- preocupações transversais de segurança quando apropriado.

Regras financeiras não devem ficar escondidas em aspects.
