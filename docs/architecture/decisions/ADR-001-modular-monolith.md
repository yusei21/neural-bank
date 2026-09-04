# ADR-001: Modular Monolith

## Status

Accepted.

## Context

O projeto é um laboratório de backend bancário que deve começar simples e evoluir para experimentos com agentes de IA.

## Decision

Adotar um monólito modular organizado por feature (`customer`, `account`, `transaction`, futuramente `ai`).

## Consequences

- Menor custo operacional e cognitivo no início.
- Transações bancárias permanecem simples de coordenar.
- Módulos podem ganhar fronteiras mais fortes antes de qualquer extração para serviços independentes.
- Microserviços não serão adotados sem necessidade concreta.
