/**
 * Pacote: insertEstudantes.js
 * Passo 4.3 do Setup: Inserção dos dados de estudantes na coleção 'estudantes'.
 */

const { COLLECTION_NAMES } = require('./createCollections');

/**
 * Insere os documentos de estudantes na coleção.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @param {Array<object>} estudantesData Array de estudantes vindo do JSON
 * @returns {Promise<import('mongodb').InsertManyResult>}
 */
async function insertEstudantes(db, estudantesData) {
  if (!estudantesData || estudantesData.length === 0) {
    console.warn('[insertEstudantes] Nenhum dado de estudante fornecido para inserção.');
    return { insertedCount: 0 };
  }

  const collection = db.collection(COLLECTION_NAMES.ESTUDANTES);
  console.log(`[insertEstudantes] Inserindo ${estudantesData.length} estudantes na coleção "${COLLECTION_NAMES.ESTUDANTES}"...`);

  const result = await collection.insertMany(estudantesData);
  console.log(`[insertEstudantes] Inserção concluída com sucesso! Total inserido: ${result.insertedCount}`);

  return result;
}

module.exports = {
  insertEstudantes,
};
