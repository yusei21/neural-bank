# Neural Bank Roadmap

Este roadmap define a ordem recomendada de evolução do projeto. A ideia é manter o Neural Bank simples no início, consolidar fundamentos de backend e só então adicionar IA, agentes e integrações mais avançadas.

## Regra de evolução

Antes de avançar para a próxima fase:

- o código da fase atual deve compilar;
- os principais casos de uso devem ter testes;
- decisões importantes devem ser registradas em `docs/`;
- cada funcionalidade deve entrar em um commit pequeno e temático;
- não adicionar infraestrutura complexa sem existir um problema real que justifique isso.

---

## v0.1.0 - Core Banking

Objetivo: construir um banco mínimo funcional e consolidar Spring Boot, REST, JPA, PostgreSQL, migrations e regras de domínio.

### Fundação

- [x] Criar projeto Spring Boot com Java 21
- [x] Configurar Maven
- [x] Configurar PostgreSQL
- [x] Adicionar Docker Compose
- [x] Adicionar `.env.example`
- [x] Configurar Flyway
- [x] Criar migration inicial
- [x] Criar estrutura modular por domínio

### Customer

- [x] Criar entidade `Customer`
- [x] Criar repository
- [x] Criar application service
- [x] Criar endpoint de cadastro
- [ ] Adicionar endpoint para buscar cliente por id
- [ ] Adicionar endpoint para listar contas do cliente
- [ ] Adicionar validação de e-mail único
- [ ] Criar testes unitários e de integração

### Account

- [x] Criar entidade `Account`
- [x] Criar `AccountStatus`
- [x] Criar repository
- [x] Criar application service
- [x] Criar endpoint para criar conta
- [x] Criar endpoint para consultar conta
- [x] Implementar depósito
- [x] Implementar saque
- [x] Impedir saldo negativo
- [ ] Adicionar número de conta independente do UUID
- [ ] Adicionar data de criação da conta
- [ ] Criar testes para todas as invariantes da conta

### Transactions

- [x] Criar entidade `Transaction`
- [x] Criar tipos de transação
- [x] Criar repository
- [x] Implementar histórico de transações
- [x] Implementar transferência atômica
- [x] Registrar débito e crédito de uma transferência
- [ ] Criar identificador compartilhado para os dois lados da transferência
- [ ] Adicionar descrição opcional da transação
- [ ] Adicionar paginação ao extrato
- [ ] Ordenar extrato por data decrescente
- [ ] Criar testes de transferência

### API

- [ ] Adicionar Springdoc OpenAPI
- [ ] Configurar Swagger UI
- [ ] Documentar requests e responses
- [ ] Padronizar códigos HTTP
- [ ] Criar modelo padrão de erro
- [ ] Adicionar `@ControllerAdvice` mais completo
- [ ] Validar payloads com Bean Validation

### Testes

- [x] Criar primeiros testes unitários do domínio
- [ ] Aumentar cobertura de testes de domínio
- [ ] Adicionar Testcontainers
- [ ] Testar PostgreSQL real em integração
- [ ] Testar migrations Flyway
- [ ] Criar testes dos controllers
- [ ] Criar teste end-to-end do fluxo cliente -> conta -> depósito -> transferência

### Critério para concluir v0.1.0

Deve ser possível executar localmente:

1. subir PostgreSQL com Docker;
2. iniciar a aplicação;
3. cadastrar dois clientes;
4. criar uma conta para cada cliente;
5. depositar dinheiro em uma conta;
6. transferir entre as contas;
7. consultar o saldo final;
8. consultar o extrato;
9. executar os testes com sucesso;
10. visualizar a API no Swagger.

---

## v0.2.0 - Banking Reliability

Objetivo: estudar problemas reais de sistemas financeiros: concorrência, consistência, idempotência, auditoria e segurança.

### Idempotência

- [ ] Entender o problema de requisições repetidas
- [ ] Criar suporte a `Idempotency-Key`
- [ ] Persistir resultados de operações idempotentes
- [ ] Impedir transferências duplicadas
- [ ] Criar testes para retries

### Concorrência

- [ ] Criar testes com operações concorrentes
- [ ] Estudar optimistic locking
- [ ] Adicionar `@Version` onde fizer sentido
- [ ] Estudar pessimistic locking para movimentações críticas
- [ ] Garantir que duas operações simultâneas não causem saldo inconsistente

### Ledger

- [ ] Estudar double-entry bookkeeping
- [ ] Separar saldo de histórico financeiro
- [ ] Criar ledger append-only
- [ ] Criar `LedgerEntry`
- [ ] Registrar crédito e débito como lançamentos imutáveis
- [ ] Calcular/reconciliar saldo com base no ledger
- [ ] Criar testes de reconciliação

### Eventos de domínio

- [ ] Criar eventos como `MoneyDeposited`
- [ ] Criar `MoneyWithdrawn`
- [ ] Criar `TransferCompleted`
- [ ] Publicar eventos somente após sucesso da transação
- [ ] Estudar `@TransactionalEventListener`

### Auditoria e POA

- [ ] Criar annotation `@Auditable`
- [ ] Criar Aspect para auditoria
- [ ] Registrar correlation id
- [ ] Registrar operação, duração e resultado
- [ ] Garantir que regras de negócio continuem fora dos Aspects
- [ ] Documentar limites de uso de POA

### Segurança

- [ ] Adicionar Spring Security
- [ ] Criar autenticação
- [ ] Criar autorização por usuário/conta
- [ ] Impedir acesso a conta de outro usuário
- [ ] Avaliar JWT para a API
- [ ] Criar testes de autorização

### Observabilidade

- [ ] Adicionar Spring Boot Actuator
- [ ] Adicionar métricas
- [ ] Adicionar tracing
- [ ] Padronizar logs estruturados
- [ ] Adicionar correlation id em requests
- [ ] Criar métricas para transferências e falhas

### Critério para concluir v0.2.0

O sistema deve suportar retries e concorrência sem duplicar operações ou corromper saldo, além de possuir autenticação, auditoria e observabilidade suficientes para investigar uma operação financeira.

---

## v0.3.0 - Neural Layer

Objetivo: integrar IA sem permitir que o modelo controle diretamente persistência ou regras bancárias.

### Spring AI

- [ ] Adicionar dependências do Spring AI
- [ ] Configurar primeiro provider de modelo
- [ ] Criar `ChatClient`
- [ ] Criar endpoint simples de chat
- [ ] Separar configuração de IA do domínio bancário

### Financial Assistant v1

Primeira versão somente leitura.

- [ ] Criar `FinancialAssistant`
- [ ] Criar system prompt
- [ ] Criar tool `getAccount`
- [ ] Criar tool `getBalance`
- [ ] Criar tool `getTransactions`
- [ ] Garantir que tools chamem application services
- [ ] Proibir acesso direto do agente a repositories
- [ ] Proibir acesso direto do agente ao banco de dados

Fluxo esperado:

```text
User
  -> Financial Assistant
      -> Tool
          -> Application Service
              -> Domain
                  -> Repository
                      -> PostgreSQL
```

### Análise financeira

- [ ] Criar resumo de transações
- [ ] Criar categorização de gastos
- [ ] Criar análise simples de hábitos financeiros
- [ ] Criar explicações sobre movimentações
- [ ] Diferenciar cálculo determinístico de inferência do modelo

### Segurança de IA

- [ ] Criar `docs/ai/safety.md`
- [ ] Documentar ferramentas permitidas
- [ ] Validar argumentos de tools
- [ ] Limitar quantidade de dados retornados ao modelo
- [ ] Não enviar dados sensíveis desnecessários ao LLM
- [ ] Registrar tool calls para auditoria
- [ ] Criar testes contra prompt injection básico

### Avaliação

- [ ] Criar conjunto fixo de perguntas de teste
- [ ] Avaliar respostas corretas/incorretas
- [ ] Avaliar seleção de tools
- [ ] Avaliar hallucinations
- [ ] Registrar experimentos em `docs/ai/experiments/`

### Critério para concluir v0.3.0

O usuário deve conseguir conversar com um assistente que consulta informações reais do Neural Bank através de tools controladas, sem acesso direto ao banco e sem capacidade de alterar estado.

---

## v0.4.0 - AI Memory and RAG

Objetivo: estudar contexto, memória e recuperação de conhecimento sem misturar esses conceitos.

### Memory

- [ ] Estudar diferença entre chat history e memória de longo prazo
- [ ] Implementar memória de conversa
- [ ] Definir limite de contexto
- [ ] Testar resumo de conversas antigas
- [ ] Avaliar o que nunca deve ser salvo como memória

### RAG

Só adicionar quando existir uma base documental útil.

- [ ] Criar documentos bancários de exemplo
- [ ] Adicionar embeddings
- [ ] Adicionar vector store
- [ ] Criar pipeline de ingestão
- [ ] Criar busca semântica
- [ ] Integrar contexto recuperado ao assistente
- [ ] Testar relevância dos documentos recuperados
- [ ] Adicionar citações/fontes às respostas

### Critério para concluir v0.4.0

O assistente deve conseguir manter contexto de conversa e responder perguntas baseadas em uma base documental sem confundir conhecimento recuperado com dados transacionais das contas.

---

## v0.5.0 - Agentic Banking

Objetivo: permitir que o agente planeje e proponha ações, mantendo confirmação humana para qualquer alteração financeira.

### Tool calling com escrita

- [ ] Criar `prepareTransfer`
- [ ] Criar objeto de operação pendente
- [ ] Mostrar destinatário, origem, valor e taxas antes da execução
- [ ] Exigir confirmação explícita do usuário
- [ ] Criar `executeTransfer` separado
- [ ] Invalidar operações expiradas
- [ ] Registrar aprovação humana

### Human-in-the-loop

Fluxo esperado:

```text
User request
   -> Agent
      -> prepare action
         -> validation
            -> user confirmation
               -> execute action
```

- [ ] Nunca executar transferência apenas com intenção inferida
- [ ] Nunca permitir confirmação criada pelo próprio modelo
- [ ] Criar testes para operações sem confirmação
- [ ] Criar auditoria completa da decisão

### Agent loop

- [ ] Estudar planning e tool selection
- [ ] Definir limite de passos por execução
- [ ] Definir timeout
- [ ] Definir custo máximo por execução
- [ ] Tratar falhas de ferramentas
- [ ] Evitar loops infinitos

### Critério para concluir v0.5.0

O agente pode preparar ações financeiras, mas nenhuma alteração crítica ocorre sem validações determinísticas e aprovação explícita do usuário.

---

## v0.6.0 - MCP Experiments

Objetivo: estudar interoperabilidade de tools e contexto entre aplicações.

- [ ] Estudar arquitetura MCP
- [ ] Criar um MCP Server experimental para consultas do Neural Bank
- [ ] Expor somente operações read-only inicialmente
- [ ] Criar tools para saldo e extrato
- [ ] Testar um MCP Client separado
- [ ] Adicionar autenticação e autorização
- [ ] Documentar ameaças de segurança
- [ ] Comparar MCP com tool calling interno do Spring AI

---

## v0.7.0 - Multi-Agent Experiments

Objetivo: estudar multi-agent somente depois que um único agente estiver funcionando e houver necessidade concreta.

### Possíveis agentes

- [ ] Financial Assistant
- [ ] Spending Analysis Agent
- [ ] Fraud Analysis Agent
- [ ] Support Agent
- [ ] Supervisor/Router Agent

### Experimentos

- [ ] Comparar single-agent vs multi-agent
- [ ] Medir latência
- [ ] Medir custo
- [ ] Medir qualidade das respostas
- [ ] Definir quando delegação realmente melhora o sistema
- [ ] Evitar agentes redundantes

### Critério para manter multi-agent

Só manter essa arquitetura se os experimentos mostrarem benefício claro sobre um único agente com boas tools.

---

## Backlog futuro

Itens que não devem bloquear as fases anteriores.

- [ ] Redis
- [ ] Cache
- [ ] Kafka
- [ ] Outbox Pattern
- [ ] Resilience4j
- [ ] Rate limiting
- [ ] CI com GitHub Actions
- [ ] Dockerfile da aplicação
- [ ] Docker Compose com aplicação + banco
- [ ] Deploy em cloud
- [ ] Kubernetes somente se houver objetivo claro de estudo
- [ ] Event sourcing como experimento separado
- [ ] CQRS como experimento separado

---

## Ordem recomendada agora

O foco imediato deve ser terminar completamente a `v0.1.0` antes de avançar para IA.

1. Adicionar Bean Validation nos requests.
2. Padronizar erros da API.
3. Adicionar OpenAPI + Swagger UI.
4. Criar testes unitários que faltam para Account e Transfer.
5. Adicionar Testcontainers.
6. Criar testes de integração com PostgreSQL.
7. Criar teste end-to-end do fluxo bancário principal.
8. Adicionar paginação ao extrato.
9. Revisar schema e constraints do PostgreSQL.
10. Fechar a `v0.1.0`.
11. Começar idempotência e concorrência da `v0.2.0`.
12. Implementar ledger.
13. Implementar segurança, auditoria e observabilidade.
14. Somente então iniciar Spring AI.

## Estratégia de commits

Continuar usando commits pequenos e específicos. Exemplos:

```text
feat(validation): validate customer creation request
feat(validation): validate transfer request
feat(web): standardize api error response
docs(openapi): document api conventions
feat(openapi): add springdoc configuration
test(account): cover withdrawal edge cases
test(transfer): cover insufficient balance
build(test): add testcontainers dependencies
test(integration): add postgres container base test
feat(transaction): paginate transaction history
```

Evitar commits grandes como:

```text
feat: finish backend
feat: add ai
fix: several things
```

O histórico do Git deve funcionar como parte da documentação do aprendizado e da evolução arquitetural do Neural Bank.
