/**
 * Domínio: Professores (Docentes)
 * Arquivo: professores.queries.js
 * 
 * Contém consultas operacionais e analíticas relacionadas aos professores.
 * Pode ser executado via Node.js ou importado em outros módulos.
 */

const { connectToDatabase } = require('../packages/createDb');

/**
 * 1. Listar todos os professores cadastrados
 * Mongosh: db.professores.find({})
 */
async function listarTodosProfessores(db) {
  return await db.collection('professores').find({}).toArray();
}

/**
 * 2. Buscar professor por Registro do Professor (RP)
 * Mongosh: db.professores.findOne({ RP: "4567890" })
 */
async function buscarProfessorPorRP(db, rp) {
  return await db.collection('professores').findOne({ RP: String(rp) });
}

/**
 * 3. Buscar professores por parte do nome (busca textual insensível a maiúsculas/minúsculas)
 * Mongosh: db.professores.find({ nome: { $regex: "Ada", $options: "i" } })
 */
async function buscarProfessoresPorNome(db, termoNome) {
  return await db.collection('professores').find({
    nome: { $regex: termoNome, $options: 'i' }
  }).toArray();
}

/**
 * 4. Listar todas as turmas e disciplinas que um professor ministra
 * Realiza cruzamento ($lookup) com a coleção 'disciplinas'.
 */
async function listarTurmasPorProfessor(db, rp) {
  return await db.collection('professores').aggregate([
    { $match: { RP: String(rp) } },
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
              totalMatriculados: '$turmas.qtdeAtualDeMatriculados',
              agendaDeAulas: '$turmas.agendaDeAulas'
            }
          }
        ],
        as: 'turmasMinistradas'
      }
    }
  ]).toArray();
}

/**
 * 5. Listar chamadas e aulas realizadas por um professor específico
 * Realiza consulta na coleção 'chamadas' filtrando pelo docente.RP.
 */
async function listarChamadasDoProfessor(db, rp) {
  return await db.collection('chamadas').find(
    { 'docente.RP': String(rp) },
    {
      projection: {
        _id: 1,
        data: 1,
        'disciplina.codigo': 1,
        'disciplina.nome': 1,
        'turma.codigo': 1,
        'aula.conteudo': 1,
        'aula.qtdeHorasAula': 1,
        resumo: 1
      }
    }
  ).sort({ data: 1 }).toArray();
}

/**
 * Exemplo de execução direta deste arquivo
 */
async function run() {
  const { client, db } = await connectToDatabase();
  try {
    console.log('--- 1. Todos os Professores ---');
    console.log(await listarTodosProfessores(db));

    console.log('\n--- 2. Professor por RP (4567890) ---');
    console.log(await buscarProfessorPorRP(db, '4567890'));

    console.log('\n--- 3. Turmas ministradas por Ada Lovelace (RP: 890392) ---');
    console.log(JSON.stringify(await listarTurmasPorProfessor(db, '890392'), null, 2));
  } finally {
    await client.close();
  }
}

if (require.main === module) {
  run().catch(console.error);
}

module.exports = {
  listarTodosProfessores,
  buscarProfessorPorRP,
  buscarProfessoresPorNome,
  listarTurmasPorProfessor,
  listarChamadasDoProfessor,
};
