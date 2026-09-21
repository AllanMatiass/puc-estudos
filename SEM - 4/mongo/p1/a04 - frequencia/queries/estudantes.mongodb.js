// =============================================================================
// DOMÍNIO: ESTUDANTES (ALUNOS)
// Banco: frequenciaDb | Coleção: estudantes
// Script para execução no MongoDB Shell (mongosh / mongo)
// =============================================================================

// Seleciona o banco de dados 'frequenciaDb'
const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" DOMÍNIO: ESTUDANTES (ALUNOS)");
print(" Banco de Dados: " + db.getName() + " | Coleção: estudantes");
print("=============================================================================");

// 1. Listar todos os estudantes
print("\n--- [1] Listar todos os estudantes cadastrados ---");
printjson(db.estudantes.find({}).toArray());

// 2. Buscar estudante por Registro de Aluno (RA)
print("\n--- [2] Buscar estudante por Registro de Aluno (RA: '1234') ---");
printjson(db.estudantes.findOne({ RA: "1234" }));

// 3. Buscar estudantes por nome (filtro com regex insensível a maiúsculas)
print("\n--- [3] Buscar estudantes por nome (filtro com regex: /Ayrton/i) ---");
printjson(db.estudantes.find({
  nome: { $regex: /Ayrton/i }
}).toArray());

// 4. Listar estudantes matriculados em uma turma específica (Disciplina 12490-P, Turma 0101)
print("\n--- [4] Estudantes matriculados na Turma 0101 da Disciplina 12490-P ---");
printjson(db.estudantes.find({
  matriculas: {
    $elemMatch: {
      disciplinaCodigo: "12490-P",
      turmaCodigo: "0101"
    }
  }
}).toArray());

// 5. Contar estudantes agrupados por curso e período
print("\n--- [5] Contagem de estudantes agrupados por curso e período ---");
printjson(db.estudantes.aggregate([
  {
    $group: {
      _id: { curso: "$curso", periodo: "$periodo" },
      totalEstudantes: { $sum: 1 },
      nomes: { $push: "$nome" }
    }
  },
  { $sort: { "_id.periodo": 1 } }
]).toArray());

// 6. Listar estudantes com situação de matrícula ativa ('matriculado')
print("\n--- [6] Listar estudantes com situação de matrícula 'matriculado' ---");
printjson(db.estudantes.find({
  "matriculas.situacao": "matriculado"
}).toArray());

print("\n=============================================================================");
print(" [OK] Consultas de Estudantes executadas com sucesso.");
print("=============================================================================\n");
