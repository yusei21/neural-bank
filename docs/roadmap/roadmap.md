# Neural Bank Roadmap

Use este arquivo como sequência de trabalho. Feche uma fase antes de abrir a próxima.

## Regras do projeto

- Faça o código compilar antes de seguir.
- Cubra os casos de uso principais com testes.
- Registre decisões em `docs/`.
- Separe cada mudança em um commit temático.
- Adicione infraestrutura quando um problema do projeto exigir.

---

## v0.1.0 - Core Banking

Construa o fluxo bancário básico com Spring Boot, REST, JPA, PostgreSQL e Flyway.

### Fundação

- [x] Criar projeto Spring Boot com Java 21
- [x] Configurar Maven
- [x] Configurar PostgreSQL
- [x] Adicionar Docker Compose
- [x] Adicionar `.env.example`
- [x] Configurar Flyway
- [x] Criar migration inicial
- [x] Organizar o código por domínio

### Customer

- [x] Criar `Customer`
- [x] Criar repository
- [x] Criar application service
- [x] Criar endpoint de cadastro
- [ ] Criar endpoint `GET /customers/{id}`
- [ ] Criar endpoint para listar contas do cliente
- [ ] Impedir cadastro de e-mail duplicado
- [ ] Testar criação e consulta de cliente

### Account

- [x] Criar `Account`
- [x] Criar `AccountStatus`
- [x] Criar repository
- [x] Criar application service
- [x] Criar endpoint para abrir conta
- [x] Criar endpoint para consultar conta
- [x] Implementar depósito
- [x] Implementar saque
- [x] Impedir saldo negativo
- [ ] Criar número de conta separado do UUID
- [ ] Registrar data de criação
- [ ] Testar depósito, saque, saldo insuficiente e valor inválido

### Transactions

- [x] Criar `Transaction`
- [x] Criar tipos de transação
- [x] Criar repository
- [x] Criar histórico de transações
- [x] Criar transferência atômica
- [x] Registrar débito e crédito da transferência
- [ ] Criar `transferId` para ligar débito e crédito
- [ ] Adicionar descrição opcional
- [ ] Paginar o extrato
- [ ] Ordenar o extrato por data decrescente
- [ ] Testar transferência entre contas
- [ ] Testar saldo insuficiente
- [ ] Testar transferência para a mesma conta

### API

- [ ] Adicionar Springdoc OpenAPI
- [ ] Configurar Swagger UI
- [ ] Documentar requests e responses
- [ ] Padronizar códigos HTTP
- [ ] Criar resposta padrão de erro
- [ ] Completar `@ControllerAdvice`
- [ ] Validar payloads com Bean Validation

### Testes

- [x] Criar testes unitários iniciais
- [ ] Cobrir regras do domínio
- [ ] Adicionar Testcontainers
- [ ] Rodar testes de integração com PostgreSQL
- [ ] Testar migrations Flyway
- [ ] Testar controllers
- [ ] Testar o fluxo completo: cliente -> conta -> depósito -> transferência -> extrato

### Fechamento da v0.1.0

Antes de criar a tag `v0.1.0`, confirme este fluxo:

1. Suba PostgreSQL com Docker.
2. Inicie a aplicação.
3. Cadastre dois clientes.
4. Abra uma conta para cada cliente.
5. Deposite dinheiro na conta de origem.
6. Faça uma transferência.
7. Consulte os dois saldos.
8. Consulte o extrato.
9. Rode os testes.
10. Abra a documentação no Swagger.

---

## v0.2.0 - Banking Reliability

Use esta fase para estudar consistência, concorrência, idempotência, auditoria e segurança.

### Idempotência

- [ ] Criar suporte ao header `Idempotency-Key`
- [ ] Persistir a chave e o resultado da operação
- [ ] Retornar o mesmo resultado em retries
- [ ] Impedir transferência duplicada
- [ ] Testar retries concorrentes

### Concorrência

- [ ] Criar testes com operações simultâneas
- [ ] Estudar optimistic locking
- [ ] Adicionar `@Version` nas entidades que precisam de controle de versão
- [ ] Testar pessimistic locking em movimentações críticas
- [ ] Garantir consistência do saldo em operações concorrentes

### Ledger

- [ ] Estudar double-entry bookkeeping
- [ ] Criar `LedgerEntry`
- [ ] Tratar lançamentos como registros imutáveis
- [ ] Registrar um débito e um crédito por transferência
- [ ] Recalcular saldo pelo ledger
- [ ] Comparar saldo armazenado com saldo calculado
- [ ] Criar testes de reconciliação

### Eventos de domínio

- [ ] Criar `MoneyDeposited`
- [ ] Criar `MoneyWithdrawn`
- [ ] Criar `TransferCompleted`
- [ ] Publicar eventos após o commit da transação
- [ ] Usar `@TransactionalEventListener`
- [ ] Testar publicação e falhas

### Auditoria e POA

- [ ] Criar `@Auditable`
- [ ] Criar um Aspect de auditoria
- [ ] Registrar correlation id
- [ ] Registrar operação, duração e resultado
- [ ] Manter regras bancárias nos services e entidades
- [ ] Documentar onde o projeto usa POA

### Segurança

- [ ] Adicionar Spring Security
- [ ] Criar autenticação
- [ ] Criar autorização por usuário e conta
- [ ] Bloquear acesso a contas de outro usuário
- [ ] Avaliar JWT
- [ ] Testar autorização

### Observabilidade

- [ ] Adicionar Spring Boot Actuator
- [ ] Expor métricas da aplicação
- [ ] Adicionar tracing
- [ ] Padronizar logs estruturados
- [ ] Propagar correlation id
- [ ] Medir transferências, falhas e latência

### Fechamento da v0.2.0

Crie a tag `v0.2.0` quando retries e concorrência não duplicarem movimentações nem corromperem saldo. Você também deve conseguir identificar quem executou uma operação, quando ela ocorreu e qual resultado produziu.

---

## v0.3.0 - Neural Layer

Adicione IA sobre os application services do banco.

### Spring AI

- [ ] Adicionar dependências do Spring AI
- [ ] Configurar um provider de modelo
- [ ] Criar `ChatClient`
- [ ] Criar endpoint de chat
- [ ] Isolar configuração de IA no módulo `ai`

### Financial Assistant v1

Comece com leitura de dados.

- [ ] Criar `FinancialAssistant`
- [ ] Criar system prompt
- [ ] Criar tool `getAccount`
- [ ] Criar tool `getBalance`
- [ ] Criar tool `getTransactions`
- [ ] Fazer cada tool chamar um application service
- [ ] Bloquear acesso do agente aos repositories
- [ ] Bloquear acesso do agente ao PostgreSQL

Fluxo:

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

- [ ] Resumir transações
- [ ] Categorizar gastos
- [ ] Identificar padrões de gastos
- [ ] Explicar movimentações
- [ ] Usar código para cálculos financeiros
- [ ] Usar o modelo para classificação e linguagem natural

### Segurança de IA

- [ ] Criar `docs/ai/safety.md`
- [ ] Listar tools permitidas
- [ ] Validar argumentos de cada tool
- [ ] Limitar dados enviados ao modelo
- [ ] Remover dados sensíveis sem uso para a tarefa
- [ ] Registrar chamadas de tools
- [ ] Testar prompt injection

### Avaliação

- [ ] Criar um conjunto fixo de perguntas
- [ ] Registrar resposta esperada para cada caso
- [ ] Medir escolha de tools
- [ ] Registrar hallucinations
- [ ] Criar experimentos em `docs/ai/experiments/`

### Fechamento da v0.3.0

Crie a tag quando o assistente consultar dados reais por tools e responder sem alterar contas, saldos ou transações.

---

## v0.4.0 - Memory and RAG

Separe memória de conversa de recuperação de documentos.

### Memory

- [ ] Implementar histórico de conversa
- [ ] Definir limite de contexto
- [ ] Resumir conversas antigas
- [ ] Definir quais dados o sistema pode guardar
- [ ] Testar recuperação de contexto entre mensagens

### RAG

Adicione RAG quando o projeto tiver documentos que o assistente precise consultar.

- [ ] Criar documentos bancários de exemplo
- [ ] Gerar embeddings
- [ ] Configurar vector store
- [ ] Criar pipeline de ingestão
- [ ] Criar busca semântica
- [ ] Entregar documentos recuperados ao modelo
- [ ] Testar relevância da busca
- [ ] Citar as fontes usadas na resposta

### Fechamento da v0.4.0

O assistente deve manter contexto da conversa e consultar documentos sem misturar esse conteúdo com saldo, conta ou transações.

---

## v0.5.0 - Agentic Banking

Permita que o agente prepare operações. O usuário confirma cada operação que altera dinheiro.

### Tools de escrita

- [ ] Criar `prepareTransfer`
- [ ] Criar `PendingOperation`
- [ ] Registrar conta de origem, destino, valor e expiração
- [ ] Mostrar os dados da operação ao usuário
- [ ] Pedir confirmação explícita
- [ ] Criar `executeTransfer`
- [ ] Exigir uma confirmação válida no backend
- [ ] Expirar operações pendentes
- [ ] Registrar quem confirmou

Fluxo:

```text
User request
   -> Agent
      -> prepareTransfer
         -> backend validation
            -> user confirmation
               -> executeTransfer
```

### Human in the loop

- [ ] Recusar execução sem confirmação
- [ ] Vincular a confirmação ao usuário autenticado
- [ ] Impedir que texto gerado pelo modelo funcione como confirmação
- [ ] Testar operação expirada
- [ ] Testar confirmação de outro usuário
- [ ] Auditar preparação e execução

### Agent loop

- [ ] Definir limite de passos
- [ ] Definir timeout
- [ ] Definir limite de custo
- [ ] Tratar falha de tool
- [ ] Interromper ciclos repetidos
- [ ] Registrar cada passo do agente para depuração

### Fechamento da v0.5.0

Crie a tag quando o agente preparar transferências e o backend exigir confirmação do usuário antes de executar cada uma.

---

## v0.6.0 - MCP

Use MCP para expor parte do Neural Bank para clientes externos de IA.

- [ ] Estudar o protocolo MCP
- [ ] Criar um MCP Server
- [ ] Expor saldo
- [ ] Expor extrato
- [ ] Manter as tools MCP em modo leitura na primeira versão
- [ ] Criar um MCP Client separado
- [ ] Adicionar autenticação
- [ ] Adicionar autorização
- [ ] Documentar ameaças e limites
- [ ] Comparar MCP com tool calling interno do Spring AI

---

## v0.7.0 - Multi-Agent

Teste multi-agent depois de medir o agente único.

### Agentes candidatos

- [ ] Financial Assistant
- [ ] Spending Analysis Agent
- [ ] Fraud Analysis Agent
- [ ] Support Agent
- [ ] Supervisor ou Router Agent

### Experimentos

- [ ] Criar a mesma tarefa no modelo single-agent
- [ ] Criar a mesma tarefa no modelo multi-agent
- [ ] Comparar latência
- [ ] Comparar custo
- [ ] Comparar acerto de tools
- [ ] Comparar qualidade das respostas
- [ ] Remover agentes que não melhorarem os resultados

Mantenha multi-agent se os testes mostrarem ganho sobre um agente único com as mesmas tools.

---

## Backlog

Trate estes itens como estudos separados. Eles não bloqueiam o core bancário nem a primeira integração com IA.

- [ ] Redis
- [ ] Cache
- [ ] Kafka
- [ ] Outbox Pattern
- [ ] Resilience4j
- [ ] Rate limiting
- [ ] CI com GitHub Actions
- [ ] Dockerfile da aplicação
- [ ] Docker Compose com aplicação e banco
- [ ] Deploy em cloud
- [ ] Kubernetes
- [ ] Event sourcing
- [ ] CQRS

---

## Próximos commits

Siga esta ordem antes de iniciar a v0.2.0:

1. Adicionar Bean Validation nos requests.
2. Criar resposta padrão de erro.
3. Completar `@ControllerAdvice`.
4. Adicionar Springdoc OpenAPI.
5. Configurar Swagger UI.
6. Testar saque e depósito.
7. Testar transferências.
8. Adicionar Testcontainers.
9. Criar teste de integração com PostgreSQL.
10. Testar migrations Flyway.
11. Criar teste do fluxo bancário completo.
12. Paginar o extrato.
13. Revisar constraints do PostgreSQL.
14. Criar a tag `v0.1.0`.

Use um commit para cada mudança quando a separação mantiver o código compilando.

```text
feat(validation): validate customer creation request
feat(validation): validate transfer request
feat(web): standardize api error response
feat(openapi): add springdoc configuration
test(account): cover withdrawal edge cases
test(transfer): cover insufficient balance
build(test): add testcontainers dependencies
test(integration): add postgres container base test
feat(transaction): paginate transaction history
```

Evite mensagens que escondem várias mudanças no mesmo commit:

```text
feat: finish backend
feat: add ai
fix: several things
```
