// =============================================================================
// DOMÍNIO: CHAMADAS E FREQUÊNCIAS
// Banco: frequenciaDb | Coleção: chamadas
// =============================================================================

use('frequenciaDb');

// 1. Listar todas as chamadas realizadas ordenadas por data
db.chamadas.find({}).sort({ data: 1 });

// 2. Buscar chamada por ID específico
db.chamadas.findOne({ _id: "CH-12490-P-0101-2026-08-01" });

// 3. Listar chamadas em uma data específica
db.chamadas.find({ data: "2026-08-01" });

// 4. Listar alunos que faltaram em uma chamada específica com suas justificativas
db.chamadas.aggregate([
  { $match: { _id: "CH-12490-P-0101-2026-08-01" } },
  { $unwind: "$alunos" },
  { $match: { "alunos.presente": false } },
  {
    $project: {
      _id: 0,
      chamadaId: "$_id",
      data: "$data",
      turma: "$turma.codigo",
      alunoRA: "$alunos.RA",
      alunoNome: "$alunos.nome",
      justificativa: { $ifNull: ["$alunos.justificativa", "Sem justificativa"] }
    }
  }
]);

// 5. Calcular taxa de presença (%) de cada aula realizada
db.chamadas.aggregate([
  {
    $project: {
      _id: 1,
      data: 1,
      disciplina: "$disciplina.codigo",
      turma: "$turma.codigo",
      docente: "$docente.nome",
      presentes: "$resumo.presentes",
      ausentes: "$resumo.ausentes",
      taxaPresenca: {
        $concat: [
          {
            $toString: {
              $multiply: [
                { $divide: ["$resumo.presentes", "$resumo.totalAlunos"] },
                100
              ]
            }
          },
          "%"
        ]
      }
    }
  }
]);
