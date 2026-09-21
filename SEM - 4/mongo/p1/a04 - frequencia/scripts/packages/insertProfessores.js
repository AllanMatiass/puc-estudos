/**
 * Pacote: insertProfessores.js
 * Passo 4.1 do Setup: Inserção dos dados de professores na coleção 'professores'.
 */

const { COLLECTION_NAMES } = require('./createCollections');

/**
 * Insere os documentos de professores na coleção.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @param {Array<object>} professoresData Array de professores vindo do JSON
 * @returns {Promise<import('mongodb').InsertManyResult>}
 */
async function insertProfessores(db, professoresData) {
  if (!professoresData || professoresData.length === 0) {
    console.warn('[insertProfessores] Nenhum dado de professor fornecido para inserção.');
    return { insertedCount: 0 };
  }

  const collection = db.collection(COLLECTION_NAMES.PROFESSORES);
  console.log(`[insertProfessores] Inserindo ${professoresData.length} professores na coleção "${COLLECTION_NAMES.PROFESSORES}"...`);

  const result = await collection.insertMany(professoresData);
  console.log(`[insertProfessores] Inserção concluída com sucesso! Total inserido: ${result.insertedCount}`);
  
  return result;
}

module.exports = {
  insertProfessores,
};
