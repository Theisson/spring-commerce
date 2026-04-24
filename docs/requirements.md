# Documento de Requisitos e Histórias de Usuário

## 1. Visão Geral
Este documento descreve os requisitos funcionais e não funcionais do sistema Spring Commerce. Os requisitos são detalhados na forma de Histórias de Usuário (User Stories) para facilitar o planejamento e o desenvolvimento iterativo.

## 2. Atores do Sistema
Identificamos três perfis principais que interagem com o sistema:
*   **Visitante (Visitor)**: Usuário anônimo não autenticado que apenas navega pelo catálogo da loja.
*   **Cliente (Customer)**: Usuário autenticado que gerencia sua conta, endereços, carteira digital e realiza pedidos.
*   **Administrador (Admin)**: Usuário autenticado com privilégios elevados, responsável pela gestão do catálogo de produtos, categorias e pelo andamento logístico dos pedidos.

### 2.1. Modelo de Identidade

O sistema adota uma separação clara entre **identidade** e **perfil comercial**:

*   **`User` (Identidade)**: Camada de autenticação e autorização. Criada no momento do registro ou do primeiro login social. É o único objeto que o sistema de segurança conhece.
*   **`Customer` (Perfil Comercial)**: Camada de negócio, criada de forma **progressiva** — apenas quando o usuário completa seus dados pessoais (CPF, telefone, data de nascimento). Uma `Wallet` é criada automaticamente junto ao `Customer`.

## 3. Requisitos Funcionais (User Stories)

As histórias de usuário foram agrupadas de acordo com os principais módulos de negócio do sistema.

### 3.1. Módulo de Catálogo (Produtos e Categorias)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-CAT-01 | Visualizar Catálogo | Como Visitante ou Cliente, quero visualizar a lista de produtos disponíveis (com paginação) para poder escolher o que comprar. | Essencial |
| US-CAT-02 | Detalhes do Produto | Como Visitante ou Cliente, quero ver os detalhes específicos de um produto (nome, descrição, preço formatado, imagem e categorias) para decidir sobre a compra. | Essencial |
| US-CAT-03 | Buscar e Filtrar Produtos | Como Visitante ou Cliente, quero buscar produtos por nome ou filtrar por categoria para encontrar itens específicos rapidamente. | Importante |
| US-CAT-04 | Gerenciar Produtos (CRUD) | Como Administrador, quero poder criar, atualizar, listar e excluir produtos (definindo o valor em dinheiro, a **quantidade em estoque** e URLs de imagem) para manter o catálogo da loja atualizado. Produtos que nunca foram associados a pedidos podem ser excluídos permanentemente. Produtos que já aparecem em pedidos devem ser **desativados**: tornam-se invisíveis no catálogo, mas permanecem íntegros no histórico de pedidos. | Essencial |
| US-CAT-05 | Gerenciar Categorias | Como Administrador, quero poder criar, editar e excluir categorias e associá-las a produtos para organizar adequadamente o inventário. | Essencial |

### 3.2. Módulo de Gestão de Usuários e Clientes
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-USR-01 | Registro de Conta | Como Visitante, quero criar uma conta no sistema informando apenas meus dados de identidade (nome de usuário, e-mail e senha) — ou autenticando via um provedor externo (ex: Google) — para que meu acesso seja estabelecido sem burocracia. Nenhum dado comercial (CPF, telefone, data de nascimento) é exigido neste momento. | Essencial |
| US-USR-02 | Autenticação e Login | Como Cliente ou Administrador, quero fazer login usando minhas credenciais (e-mail ou nome de usuário e senha) para acessar funcionalidades restritas e proteger minha conta. Quero também poder encerrar minha sessão explicitamente a qualquer momento. | Essencial |
| US-USR-03 | Gerenciar Endereços de Entrega | Como Cliente, quero poder adicionar, editar ou remover endereços de entrega (incluindo validação de CEP) no meu perfil, para poder escolher onde meus pedidos serão entregues. | Essencial |
| US-USR-04 | Gerenciar Perfil | Como Cliente, quero poder visualizar e editar meus dados de identidade (nome de usuário, e-mail), e também completar meu perfil comercial (CPF, telefone, data de nascimento) quando necessário. Ao completar o perfil comercial pela primeira vez, minha Carteira Digital é criada automaticamente. O preenchimento do perfil comercial é obrigatório antes da realização do primeiro checkout. | Importante |

### 3.3. Módulo de Carteira Digital (Wallet)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-WAL-01 | Consultar Saldo da Carteira | Como Cliente, quero visualizar o saldo atual da minha carteira digital para saber qual o meu poder de compra interno. | Essencial |
| US-WAL-02 | Adicionar Saldo | Como Cliente, quero poder adicionar fundos financeiros à minha carteira (ex: simulando um depósito ou PIX) para utilizá-los em compras futuras com aprovação instantânea. | Essencial |

### 3.4. Módulo de Pedidos e Carrinho (Orders & Checkout)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-ORD-01 | Montar Carrinho de Compras | Como Cliente, quero poder adicionar produtos e suas respectivas quantidades, gerando temporariamente uma lista de itens do pedido, para preparar a efetivação de uma compra. | Essencial |
| US-ORD-02 | Finalizar Compra (Checkout) | Como Cliente, quero revisar meu carrinho, confirmar um endereço de entrega e gerar oficialmente um pedido no sistema, que nascerá com o status de aguardando pagamento. Antes de confirmar, o sistema deve verificar se há **estoque suficiente** para cada item do carrinho, rejeitando o checkout com erro caso algum produto esteja indisponível. Ao confirmar, o estoque dos itens deve ser reservado imediatamente. | Essencial |
| US-ORD-03 | Realizar Pagamento do Pedido | Como Cliente, quero poder pagar um pedido pendente escolhendo o método de pagamento (ex: descontando o saldo da minha carteira, usando cartão de crédito ou PIX). Após a aprovação, o sistema deve gerar o registro de pagamento e alterar o status do pedido para pago. | Essencial |
| US-ORD-04 | Histórico de Pedidos | Como Cliente, quero visualizar uma lista de todos os meus pedidos passados e acompanhar os status atuais (aguardando pagamento, pago, enviado, entregue, cancelado, devolvido). | Importante |
| US-ORD-05 | Atualização Automática de Status | Como Sistema, devo atualizar automaticamente o status de um pedido com base em eventos (ex: mudança para pago após aprovação do pagamento) e integrações logísticas (webhooks de transportadoras para enviado e entregue), notificando o cliente. (O Administrador pode ter uma opção de alteração manual apenas como contingência/fallback). | Desejável |
| US-ORD-06 | Solicitar Devolução por Arrependimento | Como Cliente, quero poder solicitar a devolução de um pedido entregue dentro do prazo legal de 7 dias (direito de arrependimento, conforme o CDC), sem necessidade de justificativa, para que o valor pago seja reembolsado automaticamente na minha carteira digital e o pedido mude para o status de devolvido. | Essencial |
| US-ORD-07 | Solicitar Devolução com Motivo Comprovado | Como Cliente, quero poder abrir um chamado de solicitação de devolução para um pedido entregue há entre 8 e 30 dias, informando o motivo e anexando evidências (fotos e/ou vídeos) que comprovem o defeito ou problema com o produto, para que um atendente da loja possa analisar o meu caso. | Importante |
| US-ORD-08 | Gerenciar Chamados de Devolução | Como Administrador, quero visualizar e gerenciar os chamados de devolução abertos por clientes (incluindo as evidências anexadas), podendo aprová-los ou recusá-los, para que o pedido tenha seu status atualizado e o reembolso (se aprovado) seja processado na carteira digital do cliente. | Importante |

### 3.5. Módulo de Notificações
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-NOT-01 | Notificações de Pedido por E-mail | Como Cliente, quero receber um e-mail automático quando meu pedido for confirmado e sempre que seu status mudar (pago, enviado, entregue, cancelado, devolvido), para me manter informado sem precisar acessar o sistema. | Importante |

---

## 4. Requisitos Não Funcionais (RNF)

| Código | Objetivo | Descrição | Prioridade |
|---|---|---|---|
| **RNF-01** | Precisão Financeira Rigorosa | Todos os cálculos, atributos de preço e saldo devem utilizar obrigatoriamente o Value Object `Money` (baseado em números inteiros como `long` ou `BigDecimal`) para evitar anomalias de arredondamento de ponto flutuante. | Essencial |
| **RNF-02** | Integridade de Domínio (Tiny Types) | Dados formatados e sensíveis (`Cpf`, `Email`, `ZipCode`, `PhoneNumber`, `Username`) devem garantir suas próprias regras de validação intrínseca via Value Objects antes de qualquer tentativa de persistência no banco de dados. A senha não é representada como Value Object: sua validação de formato ocorre no `RequestDTO` (Bean Validation) e o hashing Argon2id + Pepper é aplicado no Use Case antes da persistência, sendo armazenada como `String` diretamente na entidade `User`. O campo `password` é **nullable**: é obrigatório apenas quando `authProvider = LOCAL` e deve ser nulo para usuários autenticados via provedor externo. Essa regra é garantida pelo Use Case de registro, não por constraint de banco. | Essencial |
| **RNF-03** | Segurança e Controle de Acesso | O sistema deve aplicar hashing Argon2id + Pepper na senha do usuário dentro do Use Case de registro (`RegisterUser`), antes de qualquer persistência. Essa etapa só ocorre quando `authProvider = LOCAL`; usuários de provedores externos não possuem senha armazenada. O Pepper é um segredo global da aplicação armazenado fora do banco (variável de ambiente). O sistema deve também garantir que rotas de administração não possam ser acessadas por perfis comuns de Cliente, utilizando mecanismos de segurança do framework. | Essencial |
| **RNF-04** | Consistência Associativa | A relação entre Pedidos e Produtos (`OrderItem`) deve usar obrigatoriamente a chave composta `OrderItemPK` para evitar que um mesmo produto seja duplicado no carrinho sem que a quantidade seja unificada. | Essencial |
| **RNF-05** | Desempenho e Paginação | As listagens de produtos no catálogo e histórico de pedidos devem implementar paginação no nível de banco de dados para evitar sobrecarga de memória e lentidão nas respostas da API (ex: usando `Pageable`). | Importante |
| **RNF-06** | Padronização de Exceções | A API REST deve ter um tratamento global de exceções (ex: `@ControllerAdvice`), retornando objetos de erro padronizados (com status, mensagem amigável e timestamp) para facilitar o consumo pelo frontend. | Importante |
| **RNF-07** | Autenticação Stateless com Dual-Token | A comunicação com a API deve ser *stateless*, utilizando a estratégia **dual-token**: um **Access Token JWT** de curta duração (1 hora) e um **Refresh Token opaco** de longa duração (7 dias). O Access Token é gerado e validado pelo **Spring Security OAuth2 Resource Server** *(decisão atual — revisável)*. O Refresh Token é um UUID armazenado **hasheado** na tabela `refresh_tokens` do banco de dados, permitindo revogação explícita e granular por sessão. O Access Token é enviado pelo cliente no header `Authorization: Bearer` em todas as requisições autenticadas. | Essencial |
| **RNF-08** | Integridade Referencial em Deleções | O sistema deve proteger a integridade referencial ao processar operações de exclusão. **Produtos com pedidos associados** não podem ser deletados fisicamente — o Administrador deve usar a operação de **desativação (soft delete)**: o campo `deletedAt` (timestamp) é preenchido, o produto desaparece de todas as queries públicas de catálogo, mas permanece íntegro nos `OrderItem` do histórico de pedidos. A tentativa de exclusão física de um produto nessas condições resulta em erro padronizado (`DatabaseException`). Para associações **organizacionais** (Categoria–Produto), a exclusão é permitida: ao deletar uma Categoria, o sistema remove automaticamente as associações com todos os Produtos antes de excluí-la; os Produtos permanecem intactos. | Essencial |
| **RNF-09** | Documentação da API (OpenAPI/Swagger) | A API deve ser documentada com **OpenAPI 3** via `springdoc-openapi`. Uma interface interativa deve estar disponível em `/swagger-ui.html`. Os contratos de cada endpoint devem ser descritos com `@Operation` e `@ApiResponse` nos controllers. | Importante |
| **RNF-10** | Estratégia Multi-Ambiente de Banco de Dados | O projeto deve suportar múltiplos ambientes via **Spring Profiles**: perfil `test` utiliza **H2 in-memory** para execução de testes sem dependências externas; perfil `prod` utiliza **PostgreSQL**. Um `docker-compose.yml` provê a instância local do PostgreSQL para desenvolvimento. | Essencial |
| **RNF-11** | Cache de Catálogo com Redis | A listagem e os detalhes de produtos e categorias devem ser cacheados com **Redis** usando a estratégia **Cache-Aside** (`@Cacheable`). Operações de escrita (criar, atualizar, deletar, desativar) devem invalidar o cache correspondente via `@CacheEvict`. Dados transacionais (wallet, pedidos, pagamentos, refresh tokens) **nunca** são cacheados. | Importante |
| **RNF-12** | Containerização e Ambiente Local (Docker) | O ambiente de desenvolvimento local deve ser provisionado via **Docker Compose**, com containers para **PostgreSQL**, **Redis** e **Mailpit** (servidor SMTP fake para captura de e-mails sem envio real). A aplicação Spring Boot será containerizada (Dockerfile) para execução em produção. | Importante |
| **RNF-13** | Notificações por E-mail (Resend + Mailpit) | E-mails transacionais devem ser enviados via **Resend API** em produção e capturados pelo **Mailpit** em desenvolvimento local — sem nenhuma mudança de código, apenas de configuração de profile. Os Use Cases dependem exclusivamente de `EmailNotificationPort`; `ResendEmailAdapter` e `MailpitEmailAdapter` são as implementações concretas em `integrations/`, selecionadas por Spring Profile. | Importante |
| **RNF-14** | Campos de Auditoria | As entidades principais (`User`, `Customer`, `Product`, `Order`) devem possuir os campos de auditoria `createdAt` e `updatedAt`, gerenciados automaticamente pelo Spring Data JPA via `@EnableJpaAuditing`, `@CreatedDate` e `@LastModifiedDate`. | Importante |
| **RNF-15** | Controle de Estoque | O `Product` deve possuir o campo `stockQuantity` (inteiro não-negativo). O checkout deve verificar disponibilidade e decrementar o estoque de forma atômica, utilizando **locking otimista** (`@Version` na entidade `Product`) para prevenir *overselling* em acessos concorrentes. Tentativas de checkout com estoque insuficiente resultam em erro de negócio. | Essencial |
| **RNF-16** | Gateway de Pagamento Stripe *(deferred)* | *(Implementação futura.)* Integração com **Stripe Sandbox** como gateway de pagamento real. A decisão de arquitetura entre Stripe Checkout Sessions e PaymentIntents está pendente. Webhooks da Stripe serão o mecanismo de atualização de status de pedidos (US-ORD-05). A integração seguirá o mesmo modelo de `integrations/`, com `PaymentGatewayPort` como contrato e `StripePaymentAdapter` como implementação. | Desejável |


