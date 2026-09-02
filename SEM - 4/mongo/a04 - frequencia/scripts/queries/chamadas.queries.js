/**
 * Domínio: Chamadas e Frequência das Aulas
 * Arquivo: chamadas.queries.js
 * 
 * Contém consultas para registros de chamadas diárias, presença e ausência de alunos.
 */

const { connectToDatabase } = require('../packages/createDb');

/**
 * 1. Listar todas as chamadas registradas ordenadas por data
 * Mongosh: db.chamadas.find({}).sort({ data: 1 })
 */
async function listarTodasChamadas(db) {
  return await db.collection('chamadas').find({}).sort({ data: 1 }).toArray();
}

/**
 * 2. Buscar chamada por ID da aula
 * Mongosh: db.chamadas.findOne({ _id: "CH-12490-P-0101-2026-08-01" })
 */
async function buscarChamadaPorId(db, id) {
  return await db.collection('chamadas').findOne({ _id: String(id) });
}

/**
 * 3. Listar chamadas em uma data específica
 * Mongosh: db.chamadas.find({ data: "2026-08-01" })
 */
async function listarChamadasPorData(db, dataString) {
  return await db.collection('chamadas').find({ data: dataString }).toArray();
}

/**
 * 4. Listar chamadas de uma turma específica
 */
async function listarChamadasPorTurma(db, disciplinaCodigo, turmaCodigo) {
  return await db.collection('chamadas').find({
    'disciplina.codigo': disciplinaCodigo,
    'turma.codigo': turmaCodigo,
  }).sort({ data: 1 }).toArray();
}

/**
 * 5. Listar apenas os alunos faltantes em uma chamada com sua justificativa
 */
async function listarAlunosAusentes(db, chamadaId) {
  return await db.collection('chamadas').aggregate([
    { $match: { _id: chamadaId } },
    { $unwind: '$alunos' },
    { $match: { 'alunos.presente': false } },
    {
      $project: {
        _id: 0,
        chamadaId: '$_id',
        data: '$data',
        turma: '$turma.codigo',
        alunoRA: '$alunos.RA',
        alunoNome: '$alunos.nome',
        justificativa: { $ifNull: ['$alunos.justificativa', 'Sem justificativa informada'] }
      }
    }
  ]).toArray();
}

/**
 * 6. Resumo geral de presença (taxa de presença %) de todas as chamadas
 */
async function calcularTaxaPresencaPorChamada(db) {
  return await db.collection('chamadas').aggregate([
    {
      $project: {
        _id: 1,
        data: 1,
        disciplina: '$disciplina.codigo',
        turma: '$turma.codigo',
        docente: '$docente.nome',
        totalAlunos: '$resumo.totalAlunos',
        presentes: '$resumo.presentes',
        ausentes: '$resumo.ausentes',
        taxaPresencaPercentual: {
          $multiply: [
            { $divide: ['$resumo.presentes', '$resumo.totalAlunos'] },
            100
          ]
        }
      }
    },
    { $sort: { data: 1 } }
  ]).toArray();
}

/**
 * 7. Listar histórico de conteúdos programáticos ministrados
 */
async function listarConteudosMinistrados(db) {
  return await db.collection('chamadas').find(
    {},
    {
      projection: {
        _id: 0,
        data: 1,
        'disciplina.codigo': 1,
        'turma.codigo': 1,
        'docente.nome': 1,
        'aula.conteudo': 1,
        'aula.qtdeHorasAula': 1
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
    console.log('--- 1. Todas as Chamadas ---');
    console.log(await listarTodasChamadas(db));

    console.log('\n--- 2. Alunos Ausentes na Chamada CH-12490-P-0101-2026-08-01 ---');
    console.log(await listarAlunosAusentes(db, 'CH-12490-P-0101-2026-08-01'));

    console.log('\n--- 3. Taxa de Presença por Aula ---');
    console.log(JSON.stringify(await calcularTaxaPresencaPorChamada(db), null, 2));
  } finally {
    await client.close();
  }
}

if (require.main === module) {
  run().catch(console.error);
}

module.exports = {
  listarTodasChamadas,
  buscarChamadaPorId,
  listarChamadasPorData,
  listarChamadasPorTurma,
  listarAlunosAusentes,
  calcularTaxaPresencaPorChamada,
  listarConteudosMinistrados,
};
