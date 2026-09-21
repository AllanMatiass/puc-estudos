/**
 * Pacote: insertDisciplinas.js
 * Passo 4.2 do Setup: Inserção dos dados de disciplinas na coleção 'disciplinas'.
 */

const { COLLECTION_NAMES } = require('./createCollections');

/**
 * Insere os documentos de disciplinas na coleção.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @param {Array<object>} disciplinasData Array de disciplinas vindo do JSON
 * @returns {Promise<import('mongodb').InsertManyResult>}
 */
async function insertDisciplinas(db, disciplinasData) {
  if (!disciplinasData || disciplinasData.length === 0) {
    console.warn('[insertDisciplinas] Nenhum dado de disciplina fornecido para inserção.');
    return { insertedCount: 0 };
  }

  const collection = db.collection(COLLECTION_NAMES.DISCIPLINAS);
  console.log(`[insertDisciplinas] Inserindo ${disciplinasData.length} disciplinas na coleção "${COLLECTION_NAMES.DISCIPLINAS}"...`);

  const result = await collection.insertMany(disciplinasData);
  console.log(`[insertDisciplinas] Inserção concluída com sucesso! Total inserido: ${result.insertedCount}`);

  return result;
}

module.exports = {
  insertDisciplinas,
};
