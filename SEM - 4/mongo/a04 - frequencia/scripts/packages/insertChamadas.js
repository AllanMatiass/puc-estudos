/**
 * Pacote: insertChamadas.js
 * Passo 4.4 do Setup: Inserção dos dados de chamadas na coleção 'chamadas'.
 */

const { COLLECTION_NAMES } = require('./createCollections');

/**
 * Insere os documentos de chamadas na coleção.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @param {Array<object>} chamadasData Array de chamadas vindo do JSON
 * @returns {Promise<import('mongodb').InsertManyResult>}
 */
async function insertChamadas(db, chamadasData) {
  if (!chamadasData || chamadasData.length === 0) {
    console.warn('[insertChamadas] Nenhum dado de chamada fornecido para inserção.');
    return { insertedCount: 0 };
  }

  const collection = db.collection(COLLECTION_NAMES.CHAMADAS);
  console.log(`[insertChamadas] Inserindo ${chamadasData.length} chamadas na coleção "${COLLECTION_NAMES.CHAMADAS}"...`);

  const result = await collection.insertMany(chamadasData);
  console.log(`[insertChamadas] Inserção concluída com sucesso! Total inserido: ${result.insertedCount}`);

  return result;
}

module.exports = {
  insertChamadas,
};
