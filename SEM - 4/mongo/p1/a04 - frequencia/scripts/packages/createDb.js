/**
 * Pacote: createDb.js
 * Passo 1 do Setup: Gerenciamento da conexão com o MongoDB e seleção do banco 'frequenciaDb'.
 */

const { MongoClient } = require('mongodb');
const path = require('path');
const dotenv = require('dotenv');

// Carrega as variáveis de ambiente do .env localizado na raiz do módulo a04
dotenv.config({ path: path.resolve(__dirname, '../../.env') });

const DEFAULT_DB_NAME = 'frequenciaDb';

let client = null;
let dbInstance = null;

/**
 * Constrói a string de conexão com base nas variáveis de ambiente.
 * Suporta conexão via MongoDB Atlas ou fallback para instância local.
 */
function buildConnectionString() {
  const uri = process.env.MONGODB_URI;
  const username = process.env.MONGODB_USERNAME;
  const password = process.env.MONGODB_PASSWORD;

  if (uri) {
    // Se a URI já contiver credenciais, retorna diretamente
    if (uri.includes('@')) {
      return uri;
    }
    // Se username e password estiverem definidos separadamente, insere na URI
    if (username && password) {
      const user = encodeURIComponent(username);
      const pass = encodeURIComponent(password);
      if (uri.startsWith('mongodb+srv://')) {
        return uri.replace('mongodb+srv://', `mongodb+srv://${user}:${pass}@`);
      } else if (uri.startsWith('mongodb://')) {
        return uri.replace('mongodb://', `mongodb://${user}:${pass}@`);
      }
    }
    return uri;
  }

  // Fallback padrão para MongoDB local
  return 'mongodb://127.0.0.1:27017';
}

/**
 * Conecta ao MongoDB e retorna a instância do banco de dados solicitado.
 * @param {string} dbName Nome do banco de dados (padrão: 'frequenciaDb')
 * @param {object} options Opções adicionais para o MongoClient
 * @returns {Promise<{client: MongoClient, db: Db}>}
 */
async function connectToDatabase(dbName = DEFAULT_DB_NAME, options = {}) {
  if (client && dbInstance) {
    return { client, db: dbInstance };
  }

  const connectionString = buildConnectionString();
  const maskedUri = connectionString.replace(/:([^:@]+)@/, ':****@');

  console.log(`[createDb] Conectando ao MongoDB em: ${maskedUri}`);

  client = new MongoClient(connectionString, {
    serverSelectionTimeoutMS: 8000,
    ...options,
  });

  await client.connect();
  dbInstance = client.db(dbName);

  console.log(`[createDb] Conexão estabelecida com sucesso! Banco selecionado: "${dbName}"`);
  return { client, db: dbInstance };
}

/**
 * Retorna a instância ativa do banco de dados.
 */
function getDatabase() {
  if (!dbInstance) {
    throw new Error('Banco de dados ainda não foi inicializado. Chame connectToDatabase() primeiro.');
  }
  return dbInstance;
}

/**
 * Retorna o cliente MongoClient ativo.
 */
function getClient() {
  return client;
}

/**
 * Fecha a conexão com o MongoDB de forma segura.
 */
async function closeConnection() {
  if (client) {
    await client.close();
    client = null;
    dbInstance = null;
    console.log('[createDb] Conexão com o MongoDB encerrada.');
  }
}

module.exports = {
  connectToDatabase,
  getDatabase,
  getClient,
  closeConnection,
  DEFAULT_DB_NAME,
};
