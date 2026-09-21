/**
 * Pacote: verifySetup.js
 * Passo 5 do Setup: Verificação da integridade do banco e contagem dos documentos inseridos.
 */

const { COLLECTION_NAMES } = require('./createCollections');

/**
 * Realiza uma verificação completa no banco 'frequenciaDb', listando coleções e contagens.
 * @param {import('mongodb').Db} db Instância do banco MongoDB
 * @returns {Promise<object>} Resumo da verificação
 */
async function verifySetup(db) {
  console.log('\n=============================================');
  console.log(`[verifySetup] Verificando integridade do banco: "${db.databaseName}"`);
  console.log('=============================================');

  const summary = {};
  const collections = Object.values(COLLECTION_NAMES);

  for (const colName of collections) {
    const col = db.collection(colName);
    const count = await col.countDocuments();
    const sample = await col.findOne();
    const indexes = await col.indexes();

    summary[colName] = {
      totalDocumentos: count,
      totalIndices: indexes.length,
      indices: indexes.map(idx => Object.keys(idx.key).join(', ')),
      exemploId: sample ? (sample._id ? sample._id.toString() : 'N/A') : 'Vazio',
    };

    console.log(`- Coleção "${colName}": ${count} documentos | Índices: [${summary[colName].indices.join(' | ')}]`);
  }

  console.log('=============================================');
  console.log('✓ Setup do MongoDB concluído com sucesso!');
  console.log('=============================================\n');

  return summary;
}

module.exports = {
  verifySetup,
};
