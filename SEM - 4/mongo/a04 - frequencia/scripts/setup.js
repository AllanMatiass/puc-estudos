/**
 * Setup do MongoDB: Banco de Dados 'frequenciaDb'
 * 
 * Este script orquestra a sequência de passos modulares definidos em scripts/packages:
 * 1. Conexão com o MongoDB e seleção do banco 'frequenciaDb' (createDb.js)
 * 2. Leitura e carregamento dos arquivos JSON em scripts/assets (loadAssets.js)
 * 3. Criação das coleções e configuração de índices (createCollections.js)
 * 4. Inserção dos dados nas coleções correspondentes:
 *    - professores (insertProfessores.js)
 *    - disciplinas (insertDisciplinas.js)
 *    - estudantes (insertEstudantes.js)
 *    - chamadas (insertChamadas.js)
 * 5. Verificação e exibição do resumo final (verifySetup.js)
 */

const {
  connectToDatabase,
  closeConnection,
  loadAllAssets,
  createCollections,
  insertProfessores,
  insertDisciplinas,
  insertEstudantes,
  insertChamadas,
  verifySetup,
  DEFAULT_DB_NAME,
} = require('./packages');

/**
 * Função principal que executa toda a esteira de setup
 */
async function runSetup() {
  console.log('=====================================================');
  console.log(` INICIALIZANDO SETUP DO MONGODB: ${DEFAULT_DB_NAME}`);
  console.log('=====================================================\n');

  try {
    // Passo 1: Conectar ao banco de dados
    console.log('[Passo 1/5] Conectando ao MongoDB...');
    const { db } = await connectToDatabase(DEFAULT_DB_NAME);

    // Passo 2: Carregar dados dos arquivos JSON
    console.log('\n[Passo 2/5] Carregando arquivos JSON de assets...');
    const assets = loadAllAssets();

    // Passo 3: Criar/recriar coleções e índices
    console.log('\n[Passo 3/5] Criando coleções e índices no banco...');
    await createCollections(db, true);

    // Passo 4: Inserir documentos nas respectivas coleções
    console.log('\n[Passo 4/5] Executando algoritmos de inserção dos dados...');
    await insertProfessores(db, assets.professores);
    await insertDisciplinas(db, assets.disciplinas);
    await insertEstudantes(db, assets.estudantes);
    await insertChamadas(db, assets.chamadas);

    // Passo 5: Verificar resultado final do setup
    console.log('\n[Passo 5/5] Validando e gerando resumo do setup...');
    const summary = await verifySetup(db);

    return summary;
  } catch (error) {
    console.error('\n❌ Ocorreu um erro durante a execução do setup:');
    console.error(error);
    process.exitCode = 1;
    throw error;
  } finally {
    // Encerra a conexão com o banco de dados
    await closeConnection();
  }
}

// Executa automaticamente se o arquivo for chamado diretamente via CLI
if (require.main === module) {
  runSetup()
    .then(() => {
      console.log('Processo de setup finalizado.');
    })
    .catch(() => {
      console.error('Falha no processo de setup.');
    });
}

module.exports = {
  runSetup,
};
