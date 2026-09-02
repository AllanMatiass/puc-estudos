// =============================================================================
// DOMÍNIO: PROFESSORES (DOCENTES)
// Banco: frequenciaDb | Coleção: professores
// =============================================================================

use('frequenciaDb');

// 1. Listar todos os professores cadastrados
db.professores.find({});

// 2. Buscar professor por Registro do Professor (RP)
db.professores.findOne({ RP: "4567890" });

// 3. Buscar professores por nome (filtro com regex)
db.professores.find({
  nome: { $regex: /Ada/i }
});

// 4. Listar todas as turmas que cada professor ministra (Lookup com 'disciplinas')
db.professores.aggregate([
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
            turmaCodigo: '$turmas.codigo',
            totalMatriculados: '$turmas.qtdeAtualDeMatriculados'
          }
        }
      ],
      as: 'turmasMinistradas'
    }
  }
]);

// 5. Listar chamadas e aulas realizadas por um determinado docente
db.chamadas.find(
  { 'docente.RP': "4567890" },
  {
    data: 1,
    'disciplina.nome': 1,
    'turma.codigo': 1,
    'aula.conteudo': 1,
    resumo: 1
  }
).sort({ data: 1 });
