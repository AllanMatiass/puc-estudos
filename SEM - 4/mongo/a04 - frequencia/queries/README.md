# Catálogo de Consultas — Domínio Controle de Frequência (`frequenciaDb`)

Este diretório contém consultas estruturadas para o MongoDB, divididas por domínio de negócio.

---

## 🗂️ Estrutura dos Domínios

| Domínio | Arquivo (.mongodb.js / .js) | Descrição |
| :--- | :--- | :--- |
| **Professores** | `professores.mongodb.js` / `professores.queries.js` | Busca de docentes por RP/nome, turmas lecionadas e chamadas efetuadas |
| **Estudantes** | `estudantes.mongodb.js` / `estudantes.queries.js` | Filtros por RA, matrículas por turma, contagem por curso/período |
| **Disciplinas** | `disciplinas.mongodb.js` / `disciplinas.queries.js` | Catálogo de disciplinas, agenda de aulas, vagas e ocupação |
| **Chamadas** | `chamadas.mongodb.js` / `chamadas.queries.js` | Aulas registradas, ausências com justificativa, taxa de presença |
| **Relacionamentos** | `relacionamentos.mongodb.js` / `relacionamentos.queries.js` | Agregações cruzando Professores ↔ Estudantes, histórico e faltas |

---

## 🚀 Como Executar

### Opção 1: Via MongoDB Extension (VS Code) / MongoDB Compass
Abra qualquer arquivo `.mongodb.js` e execute a query diretamente com o botão de Play ou `Ctrl + Alt + E`.

### Opção 2: Via Node.js
Na pasta `a04 - frequencia`:
```bash
# Executa demonstração completa de todos os domínios
node scripts/queries/index.js

# Ou execute um domínio específico
node scripts/queries/professores.queries.js
node scripts/queries/estudantes.queries.js
node scripts/queries/disciplinas.queries.js
node scripts/queries/chamadas.queries.js
node scripts/queries/relacionamentos.queries.js
```
