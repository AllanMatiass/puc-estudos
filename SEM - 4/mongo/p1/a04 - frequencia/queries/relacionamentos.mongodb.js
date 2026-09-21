// =============================================================================
// DOMÍNIO: RELACIONAMENTOS E CRUZAMENTOS AVANÇADOS
// Banco: frequenciaDb
// Script para execução no MongoDB Shell (mongosh / mongo)
// =============================================================================

// Seleciona o banco de dados 'frequenciaDb'
const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" DOMÍNIO: RELACIONAMENTOS E CRUZAMENTOS AVANÇADOS");
print(" Banco de Dados: " + db.getName());
print("=============================================================================");

// 1. Relação Professor -> Estudantes
// Listar todos os estudantes matriculados com o professor Steve Jobs (RP: 4567890)
print("\n--- [1] Relação Docente -> Estudantes (Steve Jobs - RP: '4567890') ---");
printjson(db.disciplinas.aggregate([
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
        { $project: { _id: 0, RA: 1, nome: 1, curso: 1, periodo: 1 } }
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
]).toArray());

// 2. Relação Estudante -> Professores
// Listar todos os professores que dão aula para Ayrton Senna (RA: 1234)
print("\n--- [2] Relação Estudante -> Professores (Ayrton Senna - RA: '1234') ---");
printjson(db.estudantes.aggregate([
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
          disciplina: "$turmaInfo.disciplina",
          turma: "$turmaInfo.turma"
        }
      }
    }
  }
]).toArray());

// 3. Histórico e Percentual de Presença individual de um estudante (RA: 1234)
print("\n--- [3] Histórico e Assiduidade de um estudante (RA: '1234') ---");
printjson(db.chamadas.aggregate([
  { $unwind: "$alunos" },
  { $match: { "alunos.RA": "1234" } },
  {
    $group: {
      _id: {
        ra: "$alunos.RA",
        nome: "$alunos.nome",
        disciplina: "$disciplina.nome",
        turma: "$turma.codigo"
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
      turma: "$_id.turma",
      totalAulas: 1,
      presencas: 1,
      faltas: 1,
      percentualPresenca: {
        $concat: [
          {
            $toString: {
              $round: [
                { $multiply: [{ $divide: ["$presencas", "$totalAulas"] }, 100] },
                1
              ]
            }
          },
          "%"
        ]
      }
    }
  }
]).toArray());

// 4. Relatório Geral de Alunos com Faltas
print("\n--- [4] Relatório Geral de Alunos com Faltas acumuladas ---");
printjson(db.chamadas.aggregate([
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
]).toArray());

// 5. Média geral de frequência por Docente e Turma
print("\n--- [5] Estatísticas de frequência agrupadas por Docente e Turma ---");
printjson(db.chamadas.aggregate([
  {
    $group: {
      _id: {
        docenteRP: "$docente.RP",
        docenteNome: "$docente.nome",
        disciplina: "$disciplina.codigo",
        turma: "$turma.codigo"
      },
      totalChamadas: { $sum: 1 },
      totalPresencas: { $sum: "$resumo.presentes" },
      totalAusencias: { $sum: "$resumo.ausentes" },
      totalAlunosAuditados: { $sum: "$resumo.totalAlunos" }
    }
  },
  {
    $project: {
      _id: 0,
      docente: { RP: "$_id.docenteRP", nome: "$_id.docenteNome" },
      disciplina: "$_id.disciplina",
      turma: "$_id.turma",
      totalChamadas: 1,
      totalPresencas: 1,
      totalAusencias: 1,
      mediaPresenca: {
        $concat: [
          {
            $toString: {
              $round: [
                { $multiply: [{ $divide: ["$totalPresencas", "$totalAlunosAuditados"] }, 100] },
                1
              ]
            }
          },
          "%"
        ]
      }
    }
  }
]).toArray());

print("\n=============================================================================");
print(" [OK] Consultas de Relacionamentos executadas com sucesso.");
print("=============================================================================\n");
