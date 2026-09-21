/**
 * Pacote: createCollections.js
 * Passo 3 do Setup: Criação das coleções e configuração de índices no 'frequenciaDb'.
 */

const COLLECTION_NAMES = {
  PROFESSORES: 'professores',
  DISCIPLINAS: 'disciplinas',
  ESTUDANTES: 'estudantes',
  CHAMADAS: 'chamadas',
};

/**
 * Cria ou recria as coleções no banco de dados e configura seus índices.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @param {boolean} dropExisting Se verdadeiro, remove as coleções existentes antes de recriar
 * @returns {Promise<object>}
 */
async function createCollections(db, dropExisting = true) {
  console.log('[createCollections] Inicializando criação das coleções...');

  const collections = Object.values(COLLECTION_NAMES);
  const existingCollections = (await db.listCollections().toArray()).map(c => c.name);

  for (const colName of collections) {
    if (existingCollections.includes(colName)) {
      if (dropExisting) {
        console.log(`[createCollections] Removendo coleção existente: "${colName}"`);
        await db.collection(colName).drop();
        await db.createCollection(colName);
        console.log(`[createCollections] Coleção "${colName}" recriada.`);
      } else {
        console.log(`[createCollections] Coleção "${colName}" já existe. Mantendo.`);
      }
    } else {
      await db.createCollection(colName);
      console.log(`[createCollections] Coleção "${colName}" criada com sucesso.`);
    }
  }

  // Configuração de índices recomendados
  console.log('[createCollections] Configurando índices nas coleções...');

  // 1. Professores: índice único por Registro do Professor (RP)
  await db.collection(COLLECTION_NAMES.PROFESSORES).createIndex({ RP: 1 }, { unique: true });

  // 2. Disciplinas: índice único por código da disciplina
  await db.collection(COLLECTION_NAMES.DISCIPLINAS).createIndex({ codigo: 1 }, { unique: true });
  await db.collection(COLLECTION_NAMES.DISCIPLINAS).createIndex({ "turmas.codigo": 1 });

  // 3. Estudantes: índice único por Registro do Aluno (RA)
  await db.collection(COLLECTION_NAMES.ESTUDANTES).createIndex({ RA: 1 }, { unique: true });
  await db.collection(COLLECTION_NAMES.ESTUDANTES).createIndex({ "matriculas.disciplinaCodigo": 1 });

  // 4. Chamadas: índices por data, disciplina.codigo e turma.codigo
  await db.collection(COLLECTION_NAMES.CHAMADAS).createIndex({ data: 1 });
  await db.collection(COLLECTION_NAMES.CHAMADAS).createIndex({ "disciplina.codigo": 1, "turma.codigo": 1 });
  await db.collection(COLLECTION_NAMES.CHAMADAS).createIndex({ "docente.RP": 1 });
  await db.collection(COLLECTION_NAMES.CHAMADAS).createIndex({ "alunos.RA": 1 });

  console.log('[createCollections] Todas as coleções e índices foram criados com sucesso!');

  return {
    professoresCol: db.collection(COLLECTION_NAMES.PROFESSORES),
    disciplinasCol: db.collection(COLLECTION_NAMES.DISCIPLINAS),
    estudantesCol: db.collection(COLLECTION_NAMES.ESTUDANTES),
    chamadasCol: db.collection(COLLECTION_NAMES.CHAMADAS),
  };
}

module.exports = {
  COLLECTION_NAMES,
  createCollections,
};
