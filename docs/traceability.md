# Rastreabilidade: Requisitos e Implementação

Este documento mapeia cada requisito do `requirements.md` ao seu status de implementação e às classes de Use Case correspondentes em `services/application/`.

**Legenda de status:**
- `Concluído` — implementado e funcional
- `Parcial` — implementado com pendências conhecidas (ver coluna Pendências)
- `Pendente` — ainda não iniciado
- `Deferido` — fora do escopo atual, previsto para iteração futura

---

## 1. Histórias de Usuário (User Stories)

### 3.1 Módulo de Catálogo

| Código | Objetivo | Status | Use Cases | Pendências |
|---|---|---|---|---|
| US-CAT-01 | Visualizar Catálogo | `Concluído` | `ListProducts` | — |
| US-CAT-02 | Detalhes do Produto | `Concluído` | `GetProduct` | — |
| US-CAT-03 | Buscar e Filtrar Produtos | `Concluído` | `ListProducts` | — |
| US-CAT-04 | Gerenciar Produtos (CRUD) | `Parcial` | `CreateProduct`, `UpdateProduct`, `DeleteProduct`, `GetProduct` | `stockQuantity` não implementado (RNF-15 pendente); soft delete (`deletedAt`) não implementado (RNF-08 pendente) |
| US-CAT-05 | Gerenciar Categorias | `Concluído` | `CreateCategory`, `UpdateCategory`, `DeleteCategory`, `GetCategory`, `ListCategories` | — |

### 3.2 Módulo de Usuários e Clientes

| Código | Objetivo | Status | Use Cases | Pendências |
|---|---|---|---|---|
| US-USR-01 | Registro de Conta | `Concluído` | `RegisterUser` | — |
| US-USR-02 | Autenticação e Login | `Pendente` | — | JWT dual-token (RNF-07) |
| US-USR-03 | Gerenciar Endereços | `Pendente` | — | — |
| US-USR-04 | Gerenciar Perfil | `Pendente` | — | Depende de US-USR-02 |

### 3.3 Módulo de Carteira Digital

| Código | Objetivo | Status | Use Cases | Pendências |
|---|---|---|---|---|
| US-WAL-01 | Consultar Saldo | `Pendente` | — | Depende de US-USR-02 |
| US-WAL-02 | Adicionar Saldo | `Pendente` | — | Depende de US-USR-02 |

### 3.4 Módulo de Pedidos e Checkout

| Código | Objetivo | Status | Use Cases | Pendências |
|---|---|---|---|---|
| US-ORD-01 | Montar Carrinho | `Pendente` | — | Depende de US-USR-02 |
| US-ORD-02 | Finalizar Compra (Checkout) | `Pendente` | — | Depende de RNF-15 (estoque) |
| US-ORD-03 | Realizar Pagamento | `Pendente` | — | — |
| US-ORD-04 | Histórico de Pedidos | `Pendente` | — | Depende de US-USR-02 |
| US-ORD-05 | Atualização Automática de Status | `Pendente` | — | Depende de US-ORD-03 e RNF-16 |
| US-ORD-06 | Devolução por Arrependimento (0–7 dias) | `Pendente` | — | — |
| US-ORD-07 | Devolução com Motivo (8–30 dias) | `Pendente` | — | — |
| US-ORD-08 | Gerenciar Chamados de Devolução | `Pendente` | — | Depende de US-ORD-07 |

### 3.5 Módulo de Notificações

| Código | Objetivo | Status | Use Cases | Pendências |
|---|---|---|---|---|
| US-NOT-01 | Notificações por E-mail | `Pendente` | — | Depende de RNF-13 (Resend + Mailpit) |

---

## 2. Requisitos Não Funcionais (RNF)

| Código | Objetivo | Status | Onde implementado | Pendências |
|---|---|---|---|---|
| RNF-01 | Precisão Financeira | `Concluído` | `models/types/Money.java`, `converters/MoneyConverter.java` | — |
| RNF-02 | Integridade de Domínio (Tiny Types) | `Concluído` | `models/types/` — `Username`, `Email`, `Cpf`, `PhoneNumber`, `ZipCode` e seus conversores em `converters/` | — |
| RNF-03 | Segurança e Controle de Acesso | `Parcial` | `config/SecurityConfig.java`, `RegisterUser.java` | Autorização por role (rotas ADMIN) pendente — depende de RNF-07 |
| RNF-04 | Consistência Associativa | `Concluído` | `models/types/OrderItemPK.java` | — |
| RNF-05 | Paginação | `Concluído` | `ListProducts.java`, `ProductRepository.java` | — |
| RNF-06 | Padronização de Exceções | `Concluído` | `controllers/handlers/GlobalExceptionHandler.java`, `dto/error/` | — |
| RNF-07 | Autenticação Stateless (Dual-Token JWT) | `Pendente` | — | Access Token JWT + Refresh Token opaco hasheado |
| RNF-08 | Integridade Referencial em Deleções | `Parcial` | `DeleteProduct.java` (protege via `DataIntegrityViolationException`) | Soft delete (`deletedAt`) ainda não implementado em `Product` |
| RNF-09 | Documentação OpenAPI/Swagger | `Pendente` | — | `springdoc-openapi` — baixa prioridade |
| RNF-10 | Multi-Ambiente (H2 / PostgreSQL) | `Parcial` | `application-test.properties` (H2 em memória) | Perfil `prod` com PostgreSQL pendente — depende de RNF-12 |
| RNF-11 | Cache com Redis | `Pendente` | — | Baixa prioridade |
| RNF-12 | Docker Compose | `Pendente` | — | Baixa prioridade |
| RNF-13 | E-mail (Resend + Mailpit) | `Pendente` | — | Contrato `integrations/EmailNotificationPort` a ser criado |
| RNF-14 | Campos de Auditoria | `Pendente` | — | `@EnableJpaAuditing` em `User`, `Customer`, `Product`, `Order` |
| RNF-15 | Controle de Estoque | `Pendente` | — | `stockQuantity` + `@Version` em `Product`; validação no checkout |
| RNF-16 | Gateway de Pagamento Stripe | `Deferido` | — | Decisão de arquitetura pendente; `PaymentGatewayPort` reservado em `integrations/` |
