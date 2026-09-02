// =============================================================================
// DOMÍNIO: DISCIPLINAS E TURMAS
// Banco: frequenciaDb | Coleção: disciplinas
// =============================================================================

use('frequenciaDb');

// 1. Listar todas as disciplinas com turmas
db.disciplinas.find({});

// 2. Buscar disciplina por código
db.disciplinas.findOne({ codigo: "12490-P" });

// 3. Listar turmas com seus respectivos docentes responsáveis
db.disciplinas.aggregate([
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
]);

// 4. Obter agenda e horários de aula da Turma 0101
db.disciplinas.aggregate([
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
]);

// 5. Buscar disciplinas ministradas por docente (RP: 4567890)
db.disciplinas.find({
  "turmas.docente.RP": "4567890"
});
