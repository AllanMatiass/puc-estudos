/**
 * main.js - Testes de Consultas (Queries) no frequenciaDb
 * 
 * Executa testes práticos de consultas nos diferentes domínios:
 * - Professores
 * - Estudantes
 * - Disciplinas
 * - Chamadas
 * - Relacionamentos / Agregações Avançadas
 */

const { connectToDatabase, closeConnection } = require('./packages/createDb');
const {
  professores,
  estudantes,
  disciplinas,
  chamadas,
  relacionamentos,
} = require('./queries');

async function main() {
  console.log('===============================================================');
  console.log('       INICIANDO TESTES DE QUERIES NO BANCO "frequenciaDb"     ');
  console.log('===============================================================\n');

  let client, db;

  try {
    // 1. Conexão com o banco
    const conn = await connectToDatabase();
    client = conn.client;
    db = conn.db;

    // -------------------------------------------------------------
    // TESTE 1: Domínio Professores
    // -------------------------------------------------------------
    console.log('🔵 [TESTE 1] Domínio Professores:');
    const todosProfessores = await professores.listarTodosProfessores(db);
    console.log('• Todos os professores cadastrados:');
    console.table(todosProfessores);

    const turmasAda = await professores.listarTurmasPorProfessor(db, '890392');
    console.log('• Turmas ministradas por Ada Lovelace (RP: 890392):');
    console.dir(turmasAda, { depth: null });

    // -------------------------------------------------------------
    // TESTE 2: Domínio Estudantes
    // -------------------------------------------------------------
    console.log('\n🟢 [TESTE 2] Domínio Estudantes:');
    const estudante = await estudantes.buscarEstudantePorRA(db, '1234');
    console.log(`• Estudante encontrado (RA: 1234): ${estudante?.nome} - Curso: ${estudante?.curso}`);

    const estudantesTurma0101 = await estudantes.listarEstudantesPorTurma(db, '12490-P', '0101');
    console.log(`• Total de alunos matriculados na Turma 0101: ${estudantesTurma0101.length}`);
    console.table(estudantesTurma0101.map(e => ({ RA: e.RA, Nome: e.nome, Periodo: e.periodo })));

    // -------------------------------------------------------------
    // TESTE 3: Domínio Disciplinas
    // -------------------------------------------------------------
    console.log('\n🟡 [TESTE 3] Domínio Disciplinas e Turmas:');
    const turmasDocentes = await disciplinas.listarTurmasComDocentes(db);
    console.log('• Visão geral de turmas e seus docentes responsáveis:');
    console.table(turmasDocentes.map(t => ({
      Disciplina: t.codigoDisciplina,
      Turma: t.turmaCodigo,
      Docente: t.docente.nome,
      'Vagas Iniciais': t.vagasIniciais,
      'Matriculados Atuais': t.matriculados
    })));

    // -------------------------------------------------------------
    // TESTE 4: Domínio Chamadas e Frequência
    // -------------------------------------------------------------
    console.log('\n🟣 [TESTE 4] Domínio Chamadas:');
    const taxasPresenca = await chamadas.calcularTaxaPresencaPorChamada(db);
    console.log('• Taxa de presença calculada por aula realizada:');
    console.table(taxasPresenca.map(c => ({
      Chamada: c._id,
      Data: c.data,
      Turma: c.turma,
      Docente: c.docente,
      Presentes: `${c.presentes}/${c.totalAlunos}`,
      'Taxa Presença': `${c.taxaPresencaPercentual.toFixed(1)}%`
    })));

    const ausentes = await chamadas.listarAlunosAusentes(db, 'CH-12490-P-0101-2026-08-01');
    console.log('• Alunos ausentes na chamada CH-12490-P-0101-2026-08-01:');
    console.table(ausentes);

    // -------------------------------------------------------------
    // TESTE 5: Relacionamentos e Cruzamento de Dados
    // -------------------------------------------------------------
    console.log('\n🔴 [TESTE 5] Relacionamentos entre Domínios:');
    
    // Relação Professor -> Alunos
    console.log('• Relação Docente -> Estudantes (Steve Jobs - RP 4567890):');
    const alunosSteve = await relacionamentos.listarEstudantesDoProfessor(db, '4567890');
    alunosSteve.forEach(t => {
      console.log(`  - Disciplina: ${t.nomeDisciplina} (Turma ${t.turma}) | Total Alunos: ${t.totalAlunos}`);
      t.estudantes.forEach(a => console.log(`    * [RA: ${a.RA}] ${a.nome}`));
    });

    // Assiduidade individual de um aluno
    console.log('\n• Histórico de Assiduidade do Aluno (Ayrton Senna - RA 1234):');
    const assiduidade = await relacionamentos.calcularAssiduidadeEstudante(db, '1234');
    console.table(assiduidade.map(a => ({
      Aluno: a.estudante.nome,
      Disciplina: a.disciplina.nome,
      Turma: a.disciplina.turma,
      Presenças: a.totalPresencas,
      Faltas: a.totalFaltas,
      'Assiduidade (%)': `${a.percentualPresenca.toFixed(1)}%`
    })));

    // Relatório de faltas
    console.log('\n• Relatório geral de faltas acumuladas por aluno:');
    const faltosos = await relacionamentos.relatorioAlunosComFaltas(db);
    console.table(faltosos.map(f => ({
      RA: f._id.RA,
      Nome: f._id.nome,
      Disciplina: f._id.disciplina,
      'Total Faltas': f.totalFaltas,
      Datas: f.ocorrencias.map(o => o.data).join(', ')
    })));

    console.log('\n===============================================================');
    console.log('            ✓ TODOS OS TESTES FORAM EXECUTADOS!               ');
    console.log('===============================================================');

  } catch (error) {
    console.error('\n❌ Erro durante a execução dos testes:', error.message);
  } finally {
    if (client) {
      await closeConnection();
    }
  }
}

// Execução
main();
