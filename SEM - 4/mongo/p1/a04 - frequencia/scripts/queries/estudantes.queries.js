/**
 * Domínio: Estudantes (Alunos)
 * Arquivo: estudantes.queries.js
 * 
 * Contém consultas relacionadas ao cadastro de estudantes e matrículas.
 */

const { connectToDatabase } = require('../packages/createDb');

/**
 * 1. Listar todos os estudantes
 * Mongosh: db.estudantes.find({})
 */
async function listarTodosEstudantes(db) {
  return await db.collection('estudantes').find({}).toArray();
}

/**
 * 2. Buscar estudante por Registro de Aluno (RA)
 * Mongosh: db.estudantes.findOne({ RA: "1234" })
 */
async function buscarEstudantePorRA(db, ra) {
  return await db.collection('estudantes').findOne({ RA: String(ra) });
}

/**
 * 3. Buscar estudantes por nome (busca com regex)
 * Mongosh: db.estudantes.find({ nome: { $regex: "Ayrton", $options: "i" } })
 */
async function buscarEstudantesPorNome(db, termoNome) {
  return await db.collection('estudantes').find({
    nome: { $regex: termoNome, $options: 'i' }
  }).toArray();
}

/**
 * 4. Listar estudantes matriculados em uma determinada disciplina e turma
 * Mongosh: db.estudantes.find({ "matriculas.disciplinaCodigo": "12490-P", "matriculas.turmaCodigo": "0101" })
 */
async function listarEstudantesPorTurma(db, disciplinaCodigo, turmaCodigo) {
  return await db.collection('estudantes').find({
    matriculas: {
      $elemMatch: {
        disciplinaCodigo: disciplinaCodigo,
        turmaCodigo: turmaCodigo,
      }
    }
  }).toArray();
}

/**
 * 5. Agrupamento e contagem de estudantes por período e curso
 * Mongosh: db.estudantes.aggregate([{ $group: { _id: { curso: "$curso", periodo: "$periodo" }, total: { $sum: 1 } } }])
 */
async function contarEstudantesPorPeriodoECurso(db) {
  return await db.collection('estudantes').aggregate([
    {
      $group: {
        _id: { curso: '$curso', periodo: '$periodo' },
        totalEstudantes: { $sum: 1 },
        alunos: { $push: { RA: '$RA', nome: '$nome' } }
      }
    },
    { $sort: { '_id.curso': 1, '_id.periodo': 1 } }
  ]).toArray();
}

/**
 * 6. Filtrar estudantes por situação da matrícula (ex: 'matriculado', 'trancado')
 */
async function listarEstudantesPorSituacao(db, situacao = 'matriculado') {
  return await db.collection('estudantes').find({
    'matriculas.situacao': situacao
  }).toArray();
}

/**
 * Exemplo de execução direta deste arquivo
 */
async function run() {
  const { client, db } = await connectToDatabase();
  try {
    console.log('--- 1. Todos os Estudantes ---');
    console.log(await listarTodosEstudantes(db));

    console.log('\n--- 2. Estudantes da Turma 0101 (Disciplina 12490-P) ---');
    console.log(await listarEstudantesPorTurma(db, '12490-P', '0101'));

    console.log('\n--- 3. Total de Estudantes por Período e Curso ---');
    console.log(JSON.stringify(await contarEstudantesPorPeriodoECurso(db), null, 2));
  } finally {
    await client.close();
  }
}

if (require.main === module) {
  run().catch(console.error);
}

module.exports = {
  listarTodosEstudantes,
  buscarEstudantePorRA,
  buscarEstudantesPorNome,
  listarEstudantesPorTurma,
  contarEstudantesPorPeriodoECurso,
  listarEstudantesPorSituacao,
};
