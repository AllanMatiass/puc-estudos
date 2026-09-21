/**
 * Pacote Principal: index.js (scripts/packages)
 * Exporta a sequência de passos e utilitários para o setup do MongoDB 'frequenciaDb'.
 */

const { connectToDatabase, getDatabase, getClient, closeConnection, DEFAULT_DB_NAME } = require('./createDb');
const { loadAllAssets, readJsonAsset, resolveAssetPath } = require('./loadAssets');
const { createCollections, COLLECTION_NAMES } = require('./createCollections');
const { insertProfessores } = require('./insertProfessores');
const { insertDisciplinas } = require('./insertDisciplinas');
const { insertEstudantes } = require('./insertEstudantes');
const { insertChamadas } = require('./insertChamadas');
const { verifySetup } = require('./verifySetup');

module.exports = {
  // Passo 1: Conexão / Criação do DB
  connectToDatabase,
  getDatabase,
  getClient,
  closeConnection,
  DEFAULT_DB_NAME,

  // Passo 2: Leitura dos Assets
  loadAllAssets,
  readJsonAsset,
  resolveAssetPath,

  // Passo 3: Criação de Coleções e Índices
  createCollections,
  COLLECTION_NAMES,

  // Passo 4: Algoritmos de Inserção por Coleção
  insertProfessores,
  insertDisciplinas,
  insertEstudantes,
  insertChamadas,

  // Passo 5: Verificação do Setup
  verifySetup,
};
