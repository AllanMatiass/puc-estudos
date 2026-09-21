// =============================================================================
// DOMÍNIO: CHAMADAS E FREQUÊNCIAS
// Banco: frequenciaDb | Coleção: chamadas
// Script para execução no MongoDB Shell (mongosh / mongo)
// =============================================================================

// Seleciona o banco de dados 'frequenciaDb'
const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" DOMÍNIO: CHAMADAS E FREQUÊNCIAS");
print(" Banco de Dados: " + db.getName() + " | Coleção: chamadas");
print("=============================================================================");

// 1. Listar todas as chamadas realizadas ordenadas por data
print("\n--- [1] Listar todas as chamadas ordenadas por data ---");
printjson(db.chamadas.find({}).sort({ data: 1 }).toArray());

// 2. Buscar chamada por ID específico
print("\n--- [2] Buscar chamada por ID ('CH-12490-P-0101-2026-08-01') ---");
printjson(db.chamadas.findOne({ _id: "CH-12490-P-0101-2026-08-01" }));

// 3. Listar chamadas em uma data específica
print("\n--- [3] Listar chamadas em uma data específica ('2026-08-01') ---");
printjson(db.chamadas.find({ data: "2026-08-01" }).toArray());

// 4. Listar chamadas de uma turma específica
print("\n--- [4] Listar chamadas da Turma 0101 da Disciplina 12490-P ---");
printjson(db.chamadas.find({
  'disciplina.codigo': '12490-P',
  'turma.codigo': '0101'
}).sort({ data: 1 }).toArray());

// 5. Listar alunos que faltaram em uma chamada específica com suas justificativas
print("\n--- [5] Alunos faltantes com justificativa na chamada CH-12490-P-0101-2026-08-01 ---");
printjson(db.chamadas.aggregate([
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
]).toArray());

// 6. Calcular taxa de presença (%) de cada aula realizada
print("\n--- [6] Taxa de presença calculada por aula realizada ---");
printjson(db.chamadas.aggregate([
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
              $round: [
                {
                  $multiply: [
                    { $divide: ["$resumo.presentes", "$resumo.totalAlunos"] },
                    100
                  ]
                },
                1
              ]
            }
          },
          "%"
        ]
      }
    }
  },
  { $sort: { data: 1 } }
]).toArray());

// 7. Listar histórico de conteúdos programáticos ministrados
print("\n--- [7] Histórico de conteúdos ministrados nas aulas ---");
printjson(db.chamadas.find(
  {},
  {
    _id: 0,
    data: 1,
    'disciplina.codigo': 1,
    'turma.codigo': 1,
    'docente.nome': 1,
    'aula.conteudo': 1,
    'aula.qtdeHorasAula': 1
  }
).sort({ data: 1 }).toArray());

print("\n=============================================================================");
print(" [OK] Consultas de Chamadas executadas com sucesso.");
print("=============================================================================");
