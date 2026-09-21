# Catálogo de Consultas — Domínio Controle de Frequência (`frequenciaDb`)

Este diretório contém consultas estruturadas para o MongoDB, divididas por domínio de negócio e preparadas como **Scripts executáveis do Mongo Shell (`mongosh` / `mongo`)** e também compatíveis com playgrounds do VS Code / MongoDB Compass.

---

## 🗂️ Estrutura dos Domínios e Scripts

| Domínio | Script Mongo Shell | Descrição |
| :--- | :--- | :--- |
| **Professores** | `professores.js` / `professores.mongodb.js` | Busca de docentes por RP/nome, turmas lecionadas e chamadas efetuadas |
| **Estudantes** | `estudantes.js` / `estudantes.mongodb.js` | Filtros por RA, matrículas por turma, contagem por curso/período |
| **Disciplinas** | `disciplinas.js` / `disciplinas.mongodb.js` | Catálogo de disciplinas, agenda de aulas, vagas e movimentação de turmas |
| **Chamadas** | `chamadas.js` / `chamadas.mongodb.js` | Aulas registradas, ausências com justificativa, taxa de presença e conteúdos |
| **Relacionamentos** | `relacionamentos.js` / `relacionamentos.mongodb.js` | Agregações cruzando Professores ↔ Estudantes, assiduidade e médias de faltas |
| **Geral (Todos)** | `index.js` | Runner que executa todas as consultas de todos os domínios sequencialmente |

---

## 🚀 Como Executar

### Opção 1: Via Mongo Shell (`mongosh`) — **Recomendado**
Execute diretamente no terminal a partir da raiz da pasta `a04 - frequencia`:

```bash
# Executar todas as consultas de todos os domínios
mongosh queries/index.js

# Ou executar um domínio específico:
mongosh queries/professores.js
mongosh queries/estudantes.js
mongosh queries/disciplinas.js
mongosh queries/chamadas.js
mongosh queries/relacionamentos.js
```

Ou dentro do console interativo do `mongosh`:
```javascript
use frequenciaDb
load("queries/professores.js")
```

### Opção 2: Setup do Banco de Dados via Mongo Shell
Caso precise recriar as coleções, índices e popular os dados via Mongo Shell:
```bash
mongosh scripts/setup.mongo.js
```

### Opção 3: Via MongoDB Extension (VS Code) / MongoDB Compass
Abra qualquer arquivo `.mongodb.js` ou `.js` na pasta `queries/` e execute diretamente com o botão de Play ou o atalho `Ctrl + Alt + E`.

### Opção 4: Via Node.js
```bash
# Executa demonstração completa de todos os domínios via driver Node.js
node scripts/queries/index.js
npm run queries
```
