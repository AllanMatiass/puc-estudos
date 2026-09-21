// =============================================================================
// DOMÍNIO: DISCIPLINAS E TURMAS
// Banco: frequenciaDb | Coleção: disciplinas
// Script para execução no MongoDB Shell (mongosh / mongo)
// =============================================================================

// Seleciona o banco de dados 'frequenciaDb'
const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" DOMÍNIO: DISCIPLINAS E TURMAS");
print(" Banco de Dados: " + db.getName() + " | Coleção: disciplinas");
print("=============================================================================");

// 1. Listar todas as disciplinas com turmas
print("\n--- [1] Listar todas as disciplinas com turmas ---");
printjson(db.disciplinas.find({}).toArray());

// 2. Buscar disciplina por código
print("\n--- [2] Buscar disciplina por código (12490-P) ---");
printjson(db.disciplinas.findOne({ codigo: "12490-P" }));

// 3. Listar turmas com seus respectivos docentes responsáveis
print("\n--- [3] Listar turmas com docentes responsáveis ---");
printjson(db.disciplinas.aggregate([
  { $unwind: "$turmas" },
  {
    $project: {
      _id: 0,
      codigoDisciplina: "$codigo",
      nomeDisciplina: "$nome",
      turmaCodigo: "$turmas.codigo",
      docente: "$turmas.docente",
      matriculados: "$turmas.qtdeAtualDeMatriculados",
      vagasIniciais: "$turmas.qtdeInicialDeMatriculados"
    }
  }
]).toArray());

// 4. Obter agenda e horários de aula da Turma 0101
print("\n--- [4] Obter agenda e horários de aula da Turma 0101 (Disciplina 12490-P) ---");
printjson(db.disciplinas.aggregate([
  { $match: { codigo: "12490-P" } },
  { $unwind: "$turmas" },
  { $match: { "turmas.codigo": "0101" } },
  {
    $project: {
      _id: 0,
      disciplina: "$nome",
      turma: "$turmas.codigo",
      docente: "$turmas.docente.nome",
      agenda: "$turmas.agendaDeAulas"
    }
  }
]).toArray());

// 5. Buscar disciplinas ministradas por docente (RP: 4567890)
print("\n--- [5] Buscar disciplinas ministradas por docente (RP: 4567890) ---");
printjson(db.disciplinas.find({
  "turmas.docente.RP": "4567890"
}).toArray());

// 6. Relatório de movimentação / evasão de turmas
print("\n--- [6] Relatório de movimentação e ocupação de vagas nas turmas ---");
printjson(db.disciplinas.aggregate([
  { $unwind: "$turmas" },
  {
    $project: {
      _id: 0,
      codigoDisciplina: "$codigo",
      turmaCodigo: "$turmas.codigo",
      docente: "$turmas.docente.nome",
      inicial: "$turmas.qtdeInicialDeMatriculados",
      atual: "$turmas.qtdeAtualDeMatriculados",
      trancamentos: "$turmas.trancamentos",
      desistencias: "$turmas.desistencias",
      transferencias: "$turmas.transferencias",
      diferenca: {
        $subtract: ["$turmas.qtdeInicialDeMatriculados", "$turmas.qtdeAtualDeMatriculados"]
      }
    }
  }
]).toArray());

print("\n=============================================================================");
print(" [OK] Consultas de Disciplinas executadas com sucesso.");
print("=============================================================================\n");
