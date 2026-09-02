// =============================================================================
// DOMÍNIO: ESTUDANTES (ALUNOS)
// Banco: frequenciaDb | Coleção: estudantes
// =============================================================================

use('frequenciaDb');

// 1. Listar todos os estudantes
db.estudantes.find({});

// 2. Buscar estudante por Registro de Aluno (RA)
db.estudantes.findOne({ RA: "1234" });

// 3. Buscar estudantes por nome
db.estudantes.find({
  nome: { $regex: /Ayrton/i }
});

// 4. Listar estudantes matriculados em uma turma específica (Disciplina 12490-P, Turma 0101)
db.estudantes.find({
  matriculas: {
    $elemMatch: {
      disciplinaCodigo: "12490-P",
      turmaCodigo: "0101"
    }
  }
});

// 5. Contar estudantes agrupados por curso e período
db.estudantes.aggregate([
  {
    $group: {
      _id: { curso: "$curso", periodo: "$periodo" },
      totalEstudantes: { $sum: 1 },
      nomes: { $push: "$nome" }
    }
  },
  { $sort: { "_id.periodo": 1 } }
]);

// 6. Listar estudantes com situação de matrícula ativa ('matriculado')
db.estudantes.find({
  "matriculas.situacao": "matriculado"
});
