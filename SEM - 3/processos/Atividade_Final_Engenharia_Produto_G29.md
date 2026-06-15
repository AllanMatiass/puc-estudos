# Atividade FINAL de Engenharia de Produto - Evolução do Produto - MesclaInvest

**Identificação:**

- **Grupo:** 29
- **Integrantes:**
  - Allan Giovanni Matias Paes (RA: 25008211)
  - Giovanna Bonfim Portela Souza (RA: 25005958)
  - Murilo Rigoni (RA: 25006049)
  - Pedro Vinícius Romanato (RA: 25004075)
  - Vinícius Castro de Oliveira (RA: 25002026)

---

## 1. Novas Personas e Cenários

### 1.1 Descrição de Nova(s) Persona(s)

Para ampliar a abrangência de atuação, cobrindo não apenas investidores casuais, mas ecossistemas completos de governança, liquidez e comunicação, mapeamos 4 novas personas:

**1. Ricardo, o Mentor Estratégico (Expert Advisor)**

- **Perfil:** Profissional sênior que busca investir seu capital intelectual (tempo/mentoria) em startups em troca de tokens (Sweat Equity).

**2. Helena, a Diretora Financeira (CFO da Startup)**

- **Perfil:** Profissional responsável pelas finanças corporativas da startup. Ela não é a fundadora original, mas precisa de acesso ao MesclaInvest para gerenciar a tesouraria, emitir lotes autorizados de tokens e preencher formulários de adequação regulatória.
- **Necessidade:** Sistema robusto de governança com perfis de acesso (RBAC) para delegar funções sem comprometer a conta master.

**3. Carlos, o Market Maker (Investidor de Alta Frequência / Day Trader)**

- **Perfil:** Investidor ativo e com alto capital, focado no mercado secundário para arbitragem.
- **Necessidade:** Agilidade em transações. Ele deseja automatizar ordens de compra/venda (Bid/Ask) via um sistema de Matching (Livro de Ofertas), sem precisar buscar manualmente ofertas ativas.

**4. Mariana, a Editora de Conteúdo (Jornalista / Assessoria MesclaInvest)**

- **Perfil:** Colaboradora da equipe MesclaInvest responsável pela integridade da informação do ecossistema.
- **Necessidade:** Ferramentas editoriais para gerenciar o fluxo de "Eventos do Mercado", auditar e aprovar os conteúdos dinâmicos gerados pelas startups.

---

### 1.2 Descrição de Novos Cenários

A evolução do produto baseia-se em 4 eixos principais interligados, desenvolvendo o ecossistema para um nível profissional de negociação e gestão:

**Cenário A: Mercado de Talentos e Governança (Sweat Equity)**
As startups publicam "Desafios de Mentoria". Profissionais se candidatam, e ao fechar o acordo, os tokens são colocados em **Escrow** (garantia) pelo sistema, sendo liberados apenas mediante a aprovação da entrega.

**Cenário B: Governança Corporativa Escalável (IAM e Valuation)**
Startups em expansão são tratadas como **Organizações (Tenants)**. O Founder utiliza o **IAM (Identity and Access Management)** para convidar a CFO (Helena) com permissões granuladas. Dentro de suas atribuições, Helena pode realizar a **Solicitação Formal de Reajuste de Preço Unitário**: caso a startup passe por um evento estrutural do mundo real (ex: recebeu aporte Série A e seu valuation quintuplicou), ela envia um formulário fundamentado para a equipe MesclaInvest, que analisa as evidências e, se aprovado, injeta um evento no motor de precificação (`Delta_Evento`), reajustando ativamente o mercado.

**Cenário C: Motor de Liquidez Automático (Matching de Ofertas)**
A negociação P2P evolui para um modelo de **Order Book (Livro de Ofertas)**. Investidores (Carlos) podem registrar intenções cegas: "Compro 500 tokens a R$10". Quando um vendedor publica uma ordem compatível, o sistema cruza as ofertas e realiza o "Match" automático (FIFO), garantindo imensa liquidez e simulando uma bolsa de valores real.

**Cenário D: Engajamento Contínuo, Destaques e Transparência**
O aplicativo ganha vida além dos números. A página da startup agora conta com um sistema de **"Destaques" (visualmente semelhante aos Stories do Instagram)**, permitindo vídeos curtos de atualizações rápidas do time, além de um **"Mural de Avisos" (Fatos Relevantes)** para comunicação direta e formal com investidores. Todos esses envios passam pela moderação editorial (Mariana/Jornalistas) antes de gerarem notificações Push para o mercado.

---

## 2. Descrição Completa (ESTADO DE PRONTO) das novas User Stories

Abaixo detalhamos as 7 histórias consolidadas que materializam os novos cenários propostos, seguindo o padrão formal de critérios de aceite exigido:

### EIXO 1: SWEAT EQUITY

#### User Story 01: Mural de Desafios de Mentoria

| Cartão de História                                                                                                                                                    | Prioridade | Story Points |
| :-------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** um Mentor Estratégico, **eu quero** visualizar e filtrar desafios técnicos publicados por startups, **para que** eu possa trocar minha expertise por tokens. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Job Board de Equity.
**Detalhamento:** O sistema apresenta um mural de vagas de mentoria pontuais onde o pagamento é feito exclusivamente em ativos da startup. A conversa foca na troca de valor entre o capital intelectual e a participação acionária.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que sou um usuário com perfil de "Mentor",
  - **When** acesso a aba "Mural de Desafios",
  - **Then** o sistema deve listar apenas desafios com status 'Ativo', exibindo a recompensa em tokens de forma destacada.
- **Critério de Validação nº 2:**
  - **Given** que apliquei um filtro por categoria "Desenvolvimento Software",
  - **When** a lista é carregada,
  - **Then** o sistema deve ocultar todos os desafios que não possuam a respectiva tag de categoria.

#### User Story 02: Garantia de Recebimento (Token Escrow)

| Cartão de História                                                                                                                                                                       | Prioridade | Story Points |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** um Fundador de Startup, **eu quero** que o sistema bloqueie os tokens da recompensa em garantia (Escrow) ao aceitar um mentor, **para que** o pagamento seja seguro para ambos. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Contrato Inteligente de Garantia.
**Detalhamento:** Ao dar o "match", os tokens saem da disponibilidade da startup mas ficam retidos em uma conta transacional segura (escrow) até a conclusão da tarefa.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que a Tesouraria da minha startup possui 5.000 tokens disponíveis,
  - **When** eu aceito um mentor para um desafio de 1.000 tokens,
  - **Then** o saldo disponível para venda deve cair para 4.000 e 1.000 tokens devem ser marcados com status 'InEscrow'.
- **Critério de Validação nº 2:**
  - **Given** que tentei aceitar um mentor para um desafio cuja recompensa é maior que meu saldo em tesouraria,
  - **When** clico em "Confirmar",
  - **Then** o sistema deve impedir a operação e exibir mensagem: "Saldo de Tesouraria insuficiente para garantir o Escrow".

#### User Story 03: Liberação de Recompensa por Milestone

| Cartão de História                                                                                                                                                                   | Prioridade | Story Points |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** um Fundador de Startup, **eu quero** aprovar entregas de marcos (milestones), **para que** os tokens em garantia sejam liberados automaticamente para a carteira do mentor. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Aperto de Mão Digital.
**Detalhamento:** É a conclusão do contrato onde o valor sai do estado bloqueado para a posse definitiva do mentor após a validação da qualidade técnica.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que um desafio foi concluído com sucesso,
  - **When** eu clico em "Aprovar Entrega Final",
  - **Then** o sistema deve debitar o saldo de Escrow e creditar o saldo na carteira (Wallet) do mentor atômicamente.
- **Critério de Validação nº 2:**
  - **Given** que a transferência foi concluída,
  - **When** o processo termina,
  - **Then** o sistema deve disparar um recálculo no `InvestmentMetricService` para melhorar o Risk Score da startup, notificando todos os investidores atuais sobre a melhoria na governança.

---

### EIXO 2: GOVERNANÇA CORPORATIVA (IAM/RBAC & VALUATION)

#### User Story 04: Controle de Acesso Baseado em Perfis (RBAC)

| Cartão de História                                                                                                                                                                  | Prioridade | Story Points |
| :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** Fundador, **eu quero** convidar membros da equipe atribuindo cargos customizados, **para que** eu possa delegar a operação financeira sem compartilhar minha senha master. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Chave Mestra e Cópias Restritas.
**Detalhamento:** Permite criar níveis de visibilidade e ação diferentes para cada membro da startup dentro da plataforma MesclaInvest.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que convidei um usuário com o papel "Somente Leitura",
  - **When** esse usuário tenta realizar uma emissão de novos tokens,
  - **Then** o botão de ação deve estar desativado e a tentativa via API deve retornar erro 403 (Unauthorized).
- **Critério de Validação nº 2:**
  - **Given** que sou o Founder,
  - **When** acesso a aba "Equipe",
  - **Then** devo ser capaz de alterar o cargo de qualquer membro ou revogar o acesso instantaneamente.

#### User Story 05: Solicitação de Reajuste de Valuation (Formulário Formal)

| Cartão de História                                                                                                                                                                      | Prioridade | Story Points |
| :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** CFO da Startup, **eu quero** preencher um formulário de Reajuste de Preço Unitário, **para que** a equipe do MesclaInvest audite nosso crescimento e atualize nossas cotações. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Auditoria de CVM.
**Detalhamento:** Permite que o crescimento fora da plataforma (mundo real) seja refletido no preço do token através de uma solicitação fundamentada.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que preenchi todos os campos do formulário de reajuste (Novo Preço, Justificativa e Anexos),
  - **When** clico em "Enviar para Auditoria",
  - **Then** o status da solicitação deve ficar "Pendente" e o formulário tornar-se somente-leitura.
- **Critério de Validação nº 2:**
  - **Given** que a equipe MesclaInvest aprovou a solicitação,
  - **When** o sistema aplica o novo preço,
  - **Then** um registro de "Evento de Reajuste Estrutural" deve aparecer no gráfico de histórico de preços da startup para todos os usuários.

---

### EIXO 3: MOTOR DE LIQUIDEZ (MATCHING)

#### User Story 06: Matching Automático de Ofertas

| Cartão de História                                                                                                                                                                       | Prioridade | Story Points |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** Market Maker (Investidor), **eu quero** inserir ordens de compra automáticas com preço alvo, **para que** o sistema cruze minha ordem quando houver uma contraparte compatível. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Bolsa de Valores Real.
**Detalhamento:** Transforma a venda direta em um Livro de Ofertas (Order Book) dinâmico que executa ordens automaticamente.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que inseri uma ordem de compra de 500 tokens a R$ 10,00,
  - **When** um vendedor insere uma ordem de venda de 500 tokens a R$ 10,00 ou menos,
  - **Then** o sistema deve realizar o "Match" imediato e transferir os ativos e o saldo automaticamente.
- **Critério de Validação nº 2:**
  - **Given** que minha ordem de compra está aberta,
  - **When** eu tento realizar outra compra manual que excede meu saldo restante,
  - **Then** o sistema deve bloquear a operação, pois o saldo da ordem aberta já está "reservado" no sistema.

---

### EIXO 4: ENGAJAMENTO (DESTAQUES E MURAL)

#### User Story 07: Moderação Editorial de Conteúdo Dinâmico

| Cartão de História                                                                                                                                                                                 | Prioridade | Story Points |
| :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :--------: | :----------: |
| **Como** Editora de Conteúdo (Admin), **eu quero** um painel para moderar vídeos de "Destaques" e "Mural de Avisos", **para que** o ecossistema mantenha a qualidade e veracidade das informações. |     -      |      -       |

**Descrição / Detalhamento / Metáfora / Modelo da História:**
**Metáfora:** Filtro Editorial.
**Detalhamento:** Garante que a comunicação entre startups e investidores seja profissional, evitando spam e informações fraudulentas.

**Protótipo (Wireframe/Figma):**

> **FAÇA e INSIRIA AQUI o link ou PRINTs da captura a conversação c/Protótipos (baixa fidelidade – Wireframe, Figma ou Adobe XD)**

**Critérios de Validação/Aceite:**

- **Critério de Validação nº 1:**
  - **Given** que uma startup enviou um novo vídeo para os "Destaques",
  - **When** eu clico em "Aprovar",
  - **Then** o vídeo deve tornar-se público no perfil da startup e uma notificação Push deve ser enviada para os seguidores.
- **Critério de Validação nº 2:**
  - **Given** que identifiquei conteúdo impróprio no "Mural de Avisos",
  - **When** eu clico em "Rejeitar",
  - **Then** o sistema deve solicitar uma justificativa obrigatória e notificar o Founder da startup sobre o motivo da recusa.

---

## 3. Decomposição das Histórias em pelo menos 2 Features

Como um Mentor Estratégico, eu quero visualizar e filtrar desafios técnicos publicados por startups, para que eu possa identificar oportunidades de trocar minha expertise por tokens.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Buscador de Desafios | Tags de categoria, Termos de busca | Acesso à aba Mural | Filtrar documentos no Firestore por tags e status 'Ativo' | Lista de Cards de Desafios filtrada |
| 2 | Sistema de Candidatura | Perfil do Mentor, Texto da Proposta | Clique em "Candidatar-se" | Criar novo documento na subcoleção 'proposals' vinculado ao usuário | Candidatura em análise e notificação ao Founder |

---

Como um Fundador de Startup, eu quero que o sistema bloqueie os tokens da recompensa em garantia (Escrow) ao aceitar um mentor, para que o pagamento seja seguro para ambos.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Lock de Tokens (Backend) | ID da Startup, Qtd Tokens da recompensa | Aceite de Proposta de mentoria | Executar Firestore Transaction para debitar Saldo Tesouraria e creditar Saldo Escrow | Registro de tokens bloqueados na tesouraria |
| 2 | Ledger de Auditoria | Dados do Match, Timestamp, IDs | Sucesso da operação de Lock | Gerar entrada imutável no log de auditoria de operações de tokens | Recibo transacional visível para auditoria |

---

Como um Fundador de Startup, eu quero aprovar entregas de marcos (milestones), para que os tokens em garantia sejam liberados automaticamente para a carteira do mentor.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Transferência Automática | ID do Escrow, ID do Mentor | Clique em "Aprovar Entrega" | Debitar saldo de Escrow e creditar na Carteira Pessoal do mentor via Transaction | Saldo disponível atualizado na carteira do mentor |
| 2 | Recalculador de Risco | Histórico de mentoria concluída | Finalização do Desafio | Invocar InvestmentMetricService para ponderar a nova governança | Novo Risk Score (0-10) atualizado na startup |

---

Como Fundador, eu quero convidar membros da equipe atribuindo cargos customizados, para que eu possa delegar a operação financeira sem compartilhar minha senha master.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Gestão de Convites (IAM) | Email do convidado, Role (CFO, Visualizador) | Envio de Convite pelo Painel ADM | Gerar token de convite e associar ao ID da Organização (Tenant) | Email de convite enviado e registro pendente |
| 2 | Injetor de Custom Claims | ID do Usuário, Permissões do Role | Login do novo membro aceito | Atualizar o JWT do Firebase Auth com as permissões granuladas (RBAC) | Sessão do usuário com acesso restrito a funções específicas |

---

Como CFO da Startup, eu quero preencher um formulário de Reajuste de Preço Unitário, para que a equipe do MesclaInvest audite nosso crescimento e atualize nossas cotações.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Gestor de Solicitações | Novo Preço, Justificativa, Anexos PDF | Submissão do formulário formal | Salvar dados em 'valuation_requests' e realizar upload para Cloud Storage | Ticket de auditoria aberto para o time MesclaInvest |
| 2 | Injetor de Delta_Evento | ID da Solicitação, Status Aprovação | Ação do Administrador MesclaInvest | Aplicar variação de preço no Pricing Engine via gatilho de Evento Estrutural | Preço atualizado em todos os portfólios e gráficos |

---

Como Market Maker (Investidor), eu quero inserir ordens de compra automáticas com preço alvo, para que o sistema cruze minha ordem quando houver uma contraparte compatível.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Gestor de Order Book | Preço Alvo, Quantidade, ID Token | Confirmação da Ordem pelo usuário | Provisionar saldo financeiro e listar ordem na coleção 'order_book' | Ordem listada no Livro de Ofertas (Market Depth) |
| 2 | Matching Engine (Trigger) | Registro de nova ordem oposta | Gravação de documento (onWrite) no Book | Buscar ordens compatíveis (Preço/Tempo) e executar troca de ativos atômicamente | Ordem executada (Match) e notificação de compra/venda |

---

Como Editora de Conteúdo (Admin), eu quero um painel para moderar vídeos de "Destaques" e "Mural de Avisos", para que o ecossistema mantenha a qualidade e veracidade das informações.
Features Derivadas:
| Seq | Nome da feature | Entradas | Ativação | Ações | Saída |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Pipeline de Moderação | Arquivos de mídia, Texto de avisos | Upload realizado pela startup | Mover conteúdo para 'quarentena' e listar no backoffice editorial | Fila de moderação visível para o Jornalista/Editor |
| 2 | Notificador Push de Fatos Relevantes | ID do Conteúdo, Decisão Aprovação | Clique em "Aprovar Conteúdo" | Mover mídia para bucket público e disparar notificação FCM para seguidores | Destaques visíveis no App e notificação push enviada |
