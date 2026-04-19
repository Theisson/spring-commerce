# Documento de Requisitos e Histórias de Usuário

## 1. Visão Geral
Este documento descreve os requisitos funcionais e não funcionais do sistema Spring Commerce. Os requisitos são detalhados na forma de Histórias de Usuário (User Stories) para facilitar o planejamento e o desenvolvimento iterativo.

## 2. Atores do Sistema
Identificamos três perfis principais que interagem com o sistema:
*   **Visitante (Visitor)**: Usuário anônimo não autenticado que apenas navega pelo catálogo da loja.
*   **Cliente (Customer)**: Usuário autenticado que gerencia sua conta, endereços, carteira digital e realiza pedidos.
*   **Administrador (Admin)**: Usuário autenticado com privilégios elevados, responsável pela gestão do catálogo de produtos, categorias e pelo andamento logístico dos pedidos.

## 3. Requisitos Funcionais (User Stories)

As histórias de usuário foram agrupadas de acordo com os principais módulos de negócio do sistema.

### 3.1. Módulo de Catálogo (Produtos e Categorias)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-CAT-01 | Visualizar Catálogo | Como Visitante ou Cliente, quero visualizar a lista de produtos disponíveis (com paginação) para poder escolher o que comprar. | Essencial |
| US-CAT-02 | Detalhes do Produto | Como Visitante ou Cliente, quero ver os detalhes específicos de um produto (nome, descrição, preço formatado, imagem e categorias) para decidir sobre a compra. | Essencial |
| US-CAT-03 | Buscar e Filtrar Produtos | Como Visitante ou Cliente, quero buscar produtos por nome ou filtrar por categoria para encontrar itens específicos rapidamente. | Importante |
| US-CAT-04 | Gerenciar Produtos (CRUD) | Como Administrador, quero poder criar, atualizar, deletar e listar produtos (definindo o valor em dinheiro e URLs de imagem) para manter o catálogo da loja atualizado. | Essencial |
| US-CAT-05 | Gerenciar Categorias | Como Administrador, quero poder criar, editar e excluir categorias e associá-las a produtos para organizar adequadamente o inventário. | Essencial |

### 3.2. Módulo de Gestão de Usuários e Clientes
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-USR-01 | Cadastro de Cliente | Como Visitante, quero me cadastrar no sistema informando meus dados pessoais (nome de usuário, e-mail, senha, CPF, data de nascimento, telefone) para que seja criada minha conta de cliente (e respectiva carteira digital). | Essencial |
| US-USR-02 | Autenticação e Login | Como Cliente ou Administrador, quero fazer login no sistema usando minhas credenciais (e-mail ou nome de usuário e senha) para acessar funcionalidades restritas e proteger minha conta. | Essencial |
| US-USR-03 | Gerenciar Endereços de Entrega | Como Cliente, quero poder adicionar, editar ou remover endereços de entrega (incluindo validação de CEP) no meu perfil, para poder escolher onde meus pedidos serão entregues. | Essencial |
| US-USR-04 | Visualizar Perfil | Como Cliente, quero visualizar e poder editar meus dados cadastrais básicos. | Importante |

### 3.3. Módulo de Carteira Digital (Wallet)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-WAL-01 | Consultar Saldo da Carteira | Como Cliente, quero visualizar o saldo atual da minha carteira digital para saber qual o meu poder de compra interno. | Essencial |
| US-WAL-02 | Adicionar Saldo | Como Cliente, quero poder adicionar fundos financeiros à minha carteira (ex: simulando um depósito ou PIX) para utilizá-los em compras futuras com aprovação instantânea. | Essencial |

### 3.4. Módulo de Pedidos e Carrinho (Orders & Checkout)
| Código | Objetivo | User Story | Prioridade |
|---|---|---|---|
| US-ORD-01 | Montar Carrinho de Compras | Como Cliente, quero poder adicionar produtos e suas respectivas quantidades, gerando temporariamente uma lista de itens do pedido, para preparar a efetivação de uma compra. | Essencial |
| US-ORD-02 | Finalizar Compra (Checkout) | Como Cliente, quero revisar meu carrinho, confirmar um endereço de entrega e gerar oficialmente um pedido no sistema, que nascerá com o status de aguardando pagamento. | Essencial |
| US-ORD-03 | Realizar Pagamento do Pedido | Como Cliente, quero poder pagar um pedido pendente escolhendo o método de pagamento (ex: descontando o saldo da minha carteira, usando cartão de crédito ou PIX). Após a aprovação, o sistema deve gerar o registro de pagamento e alterar o status do pedido para pago. | Essencial |
| US-ORD-04 | Histórico de Pedidos | Como Cliente, quero visualizar uma lista de todos os meus pedidos passados e acompanhar os status atuais (aguardando pagamento, pago, enviado, entregue, cancelado, devolvido). | Importante |
| US-ORD-05 | Atualização Automática de Status | Como Sistema, devo atualizar automaticamente o status de um pedido com base em eventos (ex: mudança para pago após aprovação do pagamento) e integrações logísticas (webhooks de transportadoras para enviado e entregue), notificando o cliente. (O Administrador pode ter uma opção de alteração manual apenas como contingência/fallback). | Desejável |
| US-ORD-06 | Solicitar Devolução por Arrependimento | Como Cliente, quero poder solicitar a devolução de um pedido entregue dentro do prazo legal de 7 dias (direito de arrependimento, conforme o CDC), sem necessidade de justificativa, para que o valor pago seja reembolsado automaticamente na minha carteira digital e o pedido mude para o status de devolvido. | Essencial |
| US-ORD-07 | Solicitar Devolução com Motivo Comprovado | Como Cliente, quero poder abrir um chamado de solicitação de devolução para um pedido entregue há entre 8 e 30 dias, informando o motivo e anexando evidências (fotos e/ou vídeos) que comprovem o defeito ou problema com o produto, para que um atendente da loja possa analisar o meu caso. | Importante |
| US-ORD-08 | Gerenciar Chamados de Devolução | Como Administrador, quero visualizar e gerenciar os chamados de devolução abertos por clientes (incluindo as evidências anexadas), podendo aprová-los ou recusá-los, para que o pedido tenha seu status atualizado e o reembolso (se aprovado) seja processado na carteira digital do cliente. | Importante |

---

## 4. Requisitos Não Funcionais (RNF)

| Código | Objetivo | Descrição | Prioridade |
|---|---|---|---|
| **RNF-01** | Precisão Financeira Rigorosa | Todos os cálculos, atributos de preço e saldo devem utilizar obrigatoriamente o Value Object `Money` (baseado em números inteiros como `long` ou `BigDecimal`) para evitar anomalias de arredondamento de ponto flutuante. | Essencial |
| **RNF-02** | Integridade de Domínio (Tiny Types) | Dados formatados e sensíveis (`Cpf`, `Email`, `ZipCode`, `PhoneNumber`, `Username`, `Password`) devem garantir suas próprias regras de validação intrínseca via Value Objects antes de qualquer tentativa de persistência no banco de dados. | Essencial |
| **RNF-03** | Segurança e Controle de Acesso | O sistema deve aplicar hashing na classe de `Password` (ex: via BCrypt) e garantir que rotas de administração não possam ser acessadas por perfis comuns de Cliente, utilizando mecanismos de segurança do framework. | Essencial |
| **RNF-04** | Consistência Associativa | A relação entre Pedidos e Produtos (`OrderItem`) deve usar obrigatoriamente a chave composta `OrderItemPK` para evitar que um mesmo produto seja duplicado no carrinho sem que a quantidade seja unificada. | Essencial |
| **RNF-05** | Desempenho e Paginação | As listagens de produtos no catálogo e histórico de pedidos devem implementar paginação no nível de banco de dados para evitar sobrecarga de memória e lentidão nas respostas da API (ex: usando `Pageable`). | Importante |
| **RNF-06** | Padronização de Exceções | A API REST deve ter um tratamento global de exceções (ex: `@ControllerAdvice`), retornando objetos de erro padronizados (com status, mensagem amigável e timestamp) para facilitar o consumo pelo frontend. | Importante |
| **RNF-07** | Autenticação Stateless | A comunicação com a API deve ser preferencialmente *stateless*, utilizando tokens (como JWT - JSON Web Tokens) para gerenciar as sessões dos usuários, facilitando a escalabilidade horizontal da aplicação. | Desejável |
| **RNF-08** | Integridade Referencial em Deleções | O sistema deve proteger a integridade referencial ao processar operações de exclusão. Recursos que possuam dependências ativas em outros registros (ex: um Produto associado a Pedidos, uma Categoria associada a Produtos) não devem ser excluídos. A tentativa deve resultar num erro padronizado (`DatabaseException`), capturado e formatado pelo `GlobalExceptionHandler`. | Essencial |