/**
 * Domínio: Disciplinas e Turmas
 * Arquivo: disciplinas.queries.js
 * 
 * Contém consultas para catálogo de disciplinas, turmas, carga horária e cronograma de aulas.
 */

const { connectToDatabase } = require('../packages/createDb');

/**
 * 1. Listar todas as disciplinas com suas turmas
 * Mongosh: db.disciplinas.find({})
 */
async function listarTodasDisciplinas(db) {
  return await db.collection('disciplinas').find({}).toArray();
}

/**
 * 2. Buscar disciplina por código
 * Mongosh: db.disciplinas.findOne({ codigo: "12490-P" })
 */
async function buscarDisciplinaPorCodigo(db, codigo) {
  return await db.collection('disciplinas').findOne({ codigo: String(codigo) });
}

/**
 * 3. Listar turmas com seus respectivos docentes responsáveis
 */
async function listarTurmasComDocentes(db) {
  return await db.collection('disciplinas').aggregate([
    { $unwind: '$turmas' },
    {
      $project: {
        _id: 0,
        codigoDisciplina: '$codigo',
        nomeDisciplina: '$nome',
        turmaCodigo: '$turmas.codigo',
        docente: '$turmas.docente',
        matriculados: '$turmas.qtdeAtualDeMatriculados',
        vagasIniciais: '$turmas.qtdeInicialDeMatriculados'
      }
    }
  ]).toArray();
}

/**
 * 4. Obter agenda detalhada de aulas de uma disciplina e turma
 */
async function obterAgendaAulas(db, codigoDisciplina, codigoTurma) {
  return await db.collection('disciplinas').aggregate([
    { $match: { codigo: codigoDisciplina } },
    { $unwind: '$turmas' },
    { $match: { 'turmas.codigo': codigoTurma } },
    {
      $project: {
        _id: 0,
        codigoDisciplina: '$codigo',
        nomeDisciplina: '$nome',
        turmaCodigo: '$turmas.codigo',
        docente: '$turmas.docente.nome',
        agendaDeAulas: '$turmas.agendaDeAulas',
        totalAulasAgendadas: { $size: '$turmas.agendaDeAulas' }
      }
    }
  ]).toArray();
}

/**
 * 5. Buscar disciplinas lecionadas por um docente (RP)
 * Mongosh: db.disciplinas.find({ "turmas.docente.RP": "4567890" })
 */
async function buscarDisciplinasPorDocenteRP(db, rp) {
  return await db.collection('disciplinas').find({
    'turmas.docente.RP': String(rp)
  }).toArray();
}

/**
 * 6. Relatório de evasão/transferência por disciplina e turma
 */
async function relatorioMovimentacaoTurmas(db) {
  return await db.collection('disciplinas').aggregate([
    { $unwind: '$turmas' },
    {
      $project: {
        _id: 0,
        codigoDisciplina: '$codigo',
        turmaCodigo: '$turmas.codigo',
        docente: '$turmas.docente.nome',
        inicial: '$turmas.qtdeInicialDeMatriculados',
        atual: '$turmas.qtdeAtualDeMatriculados',
        trancamentos: '$turmas.trancamentos',
        desistencias: '$turmas.desistencias',
        transferencias: '$turmas.transferencias',
        diferenca: {
          $subtract: ['$turmas.qtdeInicialDeMatriculados', '$turmas.qtdeAtualDeMatriculados']
        }
      }
    }
  ]).toArray();
}

/**
 * Exemplo de execução direta deste arquivo
 */
async function run() {
  const { client, db } = await connectToDatabase();
  try {
    console.log('--- 1. Todas as Disciplinas ---');
    console.log(await listarTodasDisciplinas(db));

    console.log('\n--- 2. Turmas com Docentes ---');
    console.log(await listarTurmasComDocentes(db));

    console.log('\n--- 3. Agenda da Turma 0101 ---');
    console.log(JSON.stringify(await obterAgendaAulas(db, '12490-P', '0101'), null, 2));
  } finally {
    await client.close();
  }
}

if (require.main === module) {
  run().catch(console.error);
}

module.exports = {
  listarTodasDisciplinas,
  buscarDisciplinaPorCodigo,
  listarTurmasComDocentes,
  obterAgendaAulas,
  buscarDisciplinasPorDocenteRP,
  relatorioMovimentacaoTurmas,
};
