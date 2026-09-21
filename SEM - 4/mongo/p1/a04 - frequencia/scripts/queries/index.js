/**
 * Catálogo Principal de Consultas: index.js (scripts/queries)
 * 
 * Reúne e exporta todas as consultas divididas pelos seus domínios:
 * - Professores
 * - Estudantes
 * - Disciplinas
 * - Chamadas
 * - Relacionamentos / Cruzamentos
 */

const professores = require('./professores.queries');
const estudantes = require('./estudantes.queries');
const disciplinas = require('./disciplinas.queries');
const chamadas = require('./chamadas.queries');
const relacionamentos = require('./relacionamentos.queries');
const { connectToDatabase, closeConnection } = require('../packages/createDb');

/**
 * Executa uma demonstração de consultas em todos os domínios
 */
async function executarDemonstracao() {
  console.log('=====================================================');
  console.log(' EXECUTANDO DEMONSTRAÇÃO DE CONSULTAS (frequenciaDb)');
  console.log('=====================================================\n');

  const { db } = await connectToDatabase();

  try {
    console.log('📌 [DOMÍNIO PROFESSORES] Listando todos os professores:');
    const todosProfessores = await professores.listarTodosProfessores(db);
    console.table(todosProfessores);

    console.log('\n📌 [DOMÍNIO ESTUDANTES] Contagem por período e curso:');
    const totalEstudantes = await estudantes.contarEstudantesPorPeriodoECurso(db);
    console.log(JSON.stringify(totalEstudantes, null, 2));

    console.log('\n📌 [DOMÍNIO DISCIPLINAS] Turmas com docentes responsáveis:');
    const turmasDocentes = await disciplinas.listarTurmasComDocentes(db);
    console.table(turmasDocentes.map(t => ({
      disciplina: t.codigoDisciplina,
      turma: t.turmaCodigo,
      docente: t.docente.nome,
      matriculados: t.matriculados
    })));

    console.log('\n📌 [DOMÍNIO CHAMADAS] Taxa de presença por aula realizada:');
    const taxaPresenca = await chamadas.calcularTaxaPresencaPorChamada(db);
    console.table(taxaPresenca.map(c => ({
      chamadaId: c._id,
      data: c.data,
      turma: c.turma,
      docente: c.docente,
      taxa: `${c.taxaPresencaPercentual}%`
    })));

    console.log('\n📌 [DOMÍNIO RELACIONAMENTOS] Relação Estudante -> Professores (RA: 1234):');
    const profsDoAluno = await relacionamentos.listarProfessoresDoEstudante(db, '1234');
    console.log(JSON.stringify(profsDoAluno, null, 2));

    console.log('\n📌 [DOMÍNIO RELACIONAMENTOS] Alunos com faltas registradas:');
    const alunosFaltosos = await relacionamentos.relatorioAlunosComFaltas(db);
    console.log(JSON.stringify(alunosFaltosos, null, 2));

    console.log('\n✓ Demonstração concluída com sucesso!');
  } finally {
    await closeConnection();
  }
}

if (require.main === module) {
  executarDemonstracao().catch(console.error);
}

module.exports = {
  professores,
  estudantes,
  disciplinas,
  chamadas,
  relacionamentos,
  executarDemonstracao,
};
