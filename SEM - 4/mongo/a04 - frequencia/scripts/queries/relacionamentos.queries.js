/**
 * Domínio: Relação entre Professores, Estudantes, Disciplinas e Frequência
 * Arquivo: relacionamentos.queries.js
 * 
 * Contém agregações avançadas que cruzam múltiplos domínios e coleções:
 * - Relação Professor -> Estudantes
 * - Relação Estudante -> Professores
 * - Histórico e cálculo de frequência individual do aluno
 * - Alunos em risco por excesso de faltas
 * - Estatísticas de frequência por turma e docente
 */

const { connectToDatabase } = require('../packages/createDb');

/**
 * 1. Relação Professor -> Estudantes
 * Retorna todos os alunos que são estudantes de um determinado professor (por RP)
 * Cruzamento entre a coleção 'disciplinas' (para achar as turmas do professor) e 'estudantes'.
 */
async function listarEstudantesDoProfessor(db, docenteRP) {
  return await db.collection('disciplinas').aggregate([
    { $unwind: '$turmas' },
    { $match: { 'turmas.docente.RP': String(docenteRP) } },
    {
      $lookup: {
        from: 'estudantes',
        let: { discCod: '$codigo', turmaCod: '$turmas.codigo' },
        pipeline: [
          {
            $match: {
              $expr: {
                $in: [
                  true,
                  {
                    $map: {
                      input: '$matriculas',
                      as: 'm',
                      in: {
                        $and: [
                          { $eq: ['$$m.disciplinaCodigo', '$$discCod'] },
                          { $eq: ['$$m.turmaCodigo', '$$turmaCod'] }
                        ]
                      }
                    }
                  }
                ]
              }
            }
          },
          {
            $project: {
              _id: 0,
              RA: '$RA',
              nome: '$nome',
              curso: '$curso',
              periodo: '$periodo'
            }
          }
        ],
        as: 'alunosMatriculados'
      }
    },
    {
      $project: {
        _id: 0,
        docente: '$turmas.docente',
        codigoDisciplina: '$codigo',
        nomeDisciplina: '$nome',
        turma: '$turmas.codigo',
        totalAlunos: { $size: '$alunosMatriculados' },
        estudantes: '$alunosMatriculados'
      }
    }
  ]).toArray();
}

/**
 * 2. Relação Estudante -> Professores
 * Retorna todos os professores que lecionam para um determinado aluno (por RA).
 */
async function listarProfessoresDoEstudante(db, ra) {
  return await db.collection('estudantes').aggregate([
    { $match: { RA: String(ra) } },
    { $unwind: '$matriculas' },
    {
      $lookup: {
        from: 'disciplinas',
        let: { discCod: '$matriculas.disciplinaCodigo', turmaCod: '$matriculas.turmaCodigo' },
        pipeline: [
          { $match: { $expr: { $eq: ['$codigo', '$$discCod'] } } },
          { $unwind: '$turmas' },
          { $match: { $expr: { $eq: ['$turmas.codigo', '$$turmaCod'] } } },
          {
            $project: {
              _id: 0,
              codigoDisciplina: '$codigo',
              nomeDisciplina: '$nome',
              turma: '$turmas.codigo',
              docente: '$turmas.docente'
            }
          }
        ],
        as: 'detalhesTurma'
      }
    },
    { $unwind: '$detalhesTurma' },
    {
      $group: {
        _id: '$RA',
        estudanteNome: { $first: '$nome' },
        curso: { $first: '$curso' },
        professores: {
          $addToSet: {
            RP: '$detalhesTurma.docente.RP',
            nome: '$detalhesTurma.docente.nome',
            disciplina: '$detalhesTurma.nomeDisciplina',
            turma: '$detalhesTurma.turma'
          }
        }
      }
    }
  ]).toArray();
}

/**
 * 3. Histórico completo de frequência de um estudante em todas as aulas
 * Extrai os registros do array 'alunos' dentro da coleção 'chamadas'.
 */
async function obterHistoricoFrequenciaEstudante(db, ra) {
  return await db.collection('chamadas').aggregate([
    { $unwind: '$alunos' },
    { $match: { 'alunos.RA': String(ra) } },
    {
      $project: {
        _id: 0,
        chamadaId: '$_id',
        data: '$data',
        disciplina: '$disciplina.nome',
        codigoDisciplina: '$disciplina.codigo',
        turma: '$turma.codigo',
        docente: '$docente.nome',
        conteudoAula: '$aula.conteudo',
        horasAula: '$aula.qtdeHorasAula',
        presente: '$alunos.presente',
        justificativa: '$alunos.justificativa'
      }
    },
    { $sort: { data: 1 } }
  ]).toArray();
}

/**
 * 4. Cálculo percentual de presença e faltas de um estudante em uma disciplina
 */
async function calcularAssiduidadeEstudante(db, ra, disciplinaCodigo) {
  const matchFilter = { 'alunos.RA': String(ra) };
  if (disciplinaCodigo) {
    matchFilter['disciplina.codigo'] = disciplinaCodigo;
  }

  return await db.collection('chamadas').aggregate([
    { $unwind: '$alunos' },
    { $match: matchFilter },
    {
      $group: {
        _id: {
          ra: '$alunos.RA',
          nome: '$alunos.nome',
          disciplinaCodigo: '$disciplina.codigo',
          disciplinaNome: '$disciplina.nome',
          turma: '$turma.codigo'
        },
        totalAulas: { $sum: 1 },
        totalPresencas: {
          $sum: { $cond: [{ $eq: ['$alunos.presente', true] }, 1, 0] }
        },
        totalFaltas: {
          $sum: { $cond: [{ $eq: ['$alunos.presente', false] }, 1, 0] }
        },
        totalHorasAulaMinistradas: { $sum: '$aula.qtdeHorasAula' }
      }
    },
    {
      $project: {
        _id: 0,
        estudante: { RA: '$_id.ra', nome: '$_id.nome' },
        disciplina: { codigo: '$_id.disciplinaCodigo', nome: '$_id.disciplinaNome', turma: '$_id.turma' },
        totalAulas: 1,
        totalPresencas: 1,
        totalFaltas: 1,
        totalHorasAulaMinistradas: 1,
        percentualPresenca: {
          $multiply: [{ $divide: ['$totalPresencas', '$totalAulas'] }, 100]
        }
      }
    }
  ]).toArray();
}

/**
 * 5. Relatório de alunos com ausências registradas
 */
async function relatorioAlunosComFaltas(db) {
  return await db.collection('chamadas').aggregate([
    { $unwind: '$alunos' },
    { $match: { 'alunos.presente': false } },
    {
      $group: {
        _id: { RA: '$alunos.RA', nome: '$alunos.nome', disciplina: '$disciplina.codigo' },
        totalFaltas: { $sum: 1 },
        ocorrencias: {
          $push: {
            data: '$data',
            chamadaId: '$_id',
            turma: '$turma.codigo',
            justificativa: '$alunos.justificativa'
          }
        }
      }
    },
    { $sort: { totalFaltas: -1 } }
  ]).toArray();
}

/**
 * 6. Média geral de frequência agrupada por Docente e Turma
 */
async function relatorioFrequenciaPorDocenteETurma(db) {
  return await db.collection('chamadas').aggregate([
    {
      $group: {
        _id: {
          docenteRP: '$docente.RP',
          docenteNome: '$docente.nome',
          disciplina: '$disciplina.codigo',
          turma: '$turma.codigo'
        },
        totalChamadas: { $sum: 1 },
        totalPresencasAcumuladas: { $sum: '$resumo.presentes' },
        totalAusenciasAcumuladas: { $sum: '$resumo.ausentes' },
        totalAlunosAuditados: { $sum: '$resumo.totalAlunos' }
      }
    },
    {
      $project: {
        _id: 0,
        docente: { RP: '$_id.docenteRP', nome: '$_id.docenteNome' },
        turma: '$_id.turma',
        disciplinaCodigo: '$_id.disciplina',
        totalChamadas: 1,
        totalPresencasAcumuladas: 1,
        totalAusenciasAcumuladas: 1,
        mediaPresencaTurma: {
          $multiply: [
            { $divide: ['$totalPresencasAcumuladas', '$totalAlunosAuditados'] },
            100
          ]
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
    console.log('--- 1. Estudantes do Professor Steve Jobs (RP: 4567890) ---');
    console.log(JSON.stringify(await listarEstudantesDoProfessor(db, '4567890'), null, 2));

    console.log('\n--- 2. Professores do Estudante Ayrton Senna (RA: 1234) ---');
    console.log(JSON.stringify(await listarProfessoresDoEstudante(db, '1234'), null, 2));

    console.log('\n--- 3. Assiduidade de Ayrton Senna ---');
    console.log(JSON.stringify(await calcularAssiduidadeEstudante(db, '1234'), null, 2));

    console.log('\n--- 4. Relatório de Alunos com Faltas ---');
    console.log(JSON.stringify(await relatorioAlunosComFaltas(db), null, 2));
  } finally {
    await client.close();
  }
}

if (require.main === module) {
  run().catch(console.error);
}

module.exports = {
  listarEstudantesDoProfessor,
  listarProfessoresDoEstudante,
  obterHistoricoFrequenciaEstudante,
  calcularAssiduidadeEstudante,
  relatorioAlunosComFaltas,
  relatorioFrequenciaPorDocenteETurma,
};
