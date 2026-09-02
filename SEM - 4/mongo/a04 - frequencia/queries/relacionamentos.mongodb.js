// =============================================================================
// DOMÍNIO: RELACIONAMENTOS E CRUZAMENTOS AVANÇADOS
// Banco: frequenciaDb
// =============================================================================

use('frequenciaDb');

// 1. Relação Professor -> Estudantes
// Listar todos os estudantes matriculados com o professor Steve Jobs (RP: 4567890)
db.disciplinas.aggregate([
  { $unwind: "$turmas" },
  { $match: { "turmas.docente.RP": "4567890" } },
  {
    $lookup: {
      from: "estudantes",
      let: { discCod: "$codigo", turmaCod: "$turmas.codigo" },
      pipeline: [
        {
          $match: {
            $expr: {
              $in: [
                true,
                {
                  $map: {
                    input: "$matriculas",
                    as: "m",
                    in: {
                      $and: [
                        { $eq: ["$$m.disciplinaCodigo", "$$discCod"] },
                        { $eq: ["$$m.turmaCodigo", "$$turmaCod"] }
                      ]
                    }
                  }
                }
              ]
            }
          }
        },
        { $project: { _id: 0, RA: 1, nome: 1, curso: 1 } }
      ],
      as: "estudantes"
    }
  },
  {
    $project: {
      _id: 0,
      docente: "$turmas.docente",
      disciplina: "$nome",
      turma: "$turmas.codigo",
      totalEstudantes: { $size: "$estudantes" },
      estudantes: 1
    }
  }
]);

// 2. Relação Estudante -> Professores
// Listar todos os professores que dão aula para Ayrton Senna (RA: 1234)
db.estudantes.aggregate([
  { $match: { RA: "1234" } },
  { $unwind: "$matriculas" },
  {
    $lookup: {
      from: "disciplinas",
      let: { discCod: "$matriculas.disciplinaCodigo", turmaCod: "$matriculas.turmaCodigo" },
      pipeline: [
        { $match: { $expr: { $eq: ["$codigo", "$$discCod"] } } },
        { $unwind: "$turmas" },
        { $match: { $expr: { $eq: ["$turmas.codigo", "$$turmaCod"] } } },
        {
          $project: {
            _id: 0,
            disciplina: "$nome",
            turma: "$turmas.codigo",
            docente: "$turmas.docente"
          }
        }
      ],
      as: "turmaInfo"
    }
  },
  { $unwind: "$turmaInfo" },
  {
    $group: {
      _id: "$RA",
      aluno: { $first: "$nome" },
      professores: {
        $addToSet: {
          nome: "$turmaInfo.docente.nome",
          RP: "$turmaInfo.docente.RP",
          disciplina: "$turmaInfo.disciplina"
        }
      }
    }
  }
]);

// 3. Histórico e Percentual de Presença individual de um estudante (RA: 1234)
db.chamadas.aggregate([
  { $unwind: "$alunos" },
  { $match: { "alunos.RA": "1234" } },
  {
    $group: {
      _id: {
        ra: "$alunos.RA",
        nome: "$alunos.nome",
        disciplina: "$disciplina.nome"
      },
      totalAulas: { $sum: 1 },
      presencas: { $sum: { $cond: [{ $eq: ["$alunos.presente", true] }, 1, 0] } },
      faltas: { $sum: { $cond: [{ $eq: ["$alunos.presente", false] }, 1, 0] } }
    }
  },
  {
    $project: {
      _id: 0,
      estudante: "$_id.nome",
      disciplina: "$_id.disciplina",
      totalAulas: 1,
      presencas: 1,
      faltas: 1,
      percentualPresenca: {
        $concat: [
          {
            $toString: {
              $multiply: [{ $divide: ["$presencas", "$totalAulas"] }, 100]
            }
          },
          "%"
        ]
      }
    }
  }
]);

// 4. Relatório Geral de Alunos com Faltas
db.chamadas.aggregate([
  { $unwind: "$alunos" },
  { $match: { "alunos.presente": false } },
  {
    $group: {
      _id: { RA: "$alunos.RA", nome: "$alunos.nome" },
      totalFaltas: { $sum: 1 },
      detalhesFaltas: {
        $push: {
          data: "$data",
          disciplina: "$disciplina.codigo",
          turma: "$turma.codigo",
          justificativa: "$alunos.justificativa"
        }
      }
    }
  },
  { $sort: { totalFaltas: -1 } }
]);
