// =============================================================================
// DOMÍNIO: PROFESSORES (DOCENTES)
// Banco: frequenciaDb | Coleção: professores
// Script para execução no MongoDB Shell (mongosh / mongo)
// =============================================================================

// Seleciona o banco de dados 'frequenciaDb'
const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" DOMÍNIO: PROFESSORES (DOCENTES)");
print(" Banco de Dados: " + db.getName() + " | Coleção: professores");
print("=============================================================================\n");

// 1. Listar todos os professores cadastrados
print("--- [1] Listar todos os professores cadastrados ---");
printjson(db.professores.find({}).toArray());

// 2. Buscar professor por Registro do Professor (RP)
print("\n--- [2] Buscar professor por Registro do Professor (RP: '4567890') ---");
printjson(db.professores.findOne({ RP: "4567890" }));

// 3. Buscar professores por nome (filtro com regex insensível a maiúsculas)
print("\n--- [3] Buscar professores por nome (filtro com regex: /Ada/i) ---");
printjson(db.professores.find({
  nome: { $regex: /Ada/i }
}).toArray());

// 4. Listar todas as turmas que cada professor ministra (Lookup com 'disciplinas')
print("\n--- [4] Listar turmas que cada professor ministra (Lookup com 'disciplinas') ---");
printjson(db.professores.aggregate([
  {
    $lookup: {
      from: 'disciplinas',
      let: { profRp: '$RP' },
      pipeline: [
        { $unwind: '$turmas' },
        { $match: { $expr: { $eq: ['$turmas.docente.RP', '$$profRp'] } } },
        {
          $project: {
            _id: 0,
            codigoDisciplina: '$codigo',
            nomeDisciplina: '$nome',
            curso: '$curso',
            periodo: '$periodo',
            turmaCodigo: '$turmas.codigo',
            totalMatriculados: '$turmas.qtdeAtualDeMatriculados'
          }
        }
      ],
      as: 'turmasMinistradas'
    }
  }
]).toArray());

// 5. Listar chamadas e aulas realizadas por um determinado docente (RP: '4567890')
print("\n--- [5] Listar chamadas e aulas realizadas pelo docente (RP: '4567890') ---");
printjson(db.chamadas.find(
  { 'docente.RP': "4567890" },
  {
    data: 1,
    'disciplina.nome': 1,
    'turma.codigo': 1,
    'aula.conteudo': 1,
    'aula.qtdeHorasAula': 1,
    resumo: 1
  }
).sort({ data: 1 }).toArray());

print("\n=============================================================================");
print(" [OK] Consultas de Professores executadas com sucesso.");
print("=============================================================================\n");
