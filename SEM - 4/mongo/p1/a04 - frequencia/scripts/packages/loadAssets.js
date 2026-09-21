/**
 * Pacote: loadAssets.js
 * Passo 2 do Setup: Leitura e parseamento dos arquivos JSON contidos em scripts/assets.
 */

const fs = require('fs');
const path = require('path');

/**
 * Procura um arquivo nos diretórios possíveis de assets
 * @param {string} fileName Nome do arquivo JSON
 * @returns {string} Caminho absoluto do arquivo
 */
function resolveAssetPath(fileName) {
  const possiblePaths = [
    path.resolve(__dirname, '../assets/controle-frequencia', fileName),
    path.resolve(__dirname, '../assets', fileName),
  ];

  for (const p of possiblePaths) {
    if (fs.existsSync(p)) {
      return p;
    }
  }

  throw new Error(`Arquivo de asset não encontrado: ${fileName}. Verifique a pasta scripts/assets/`);
}

/**
 * Lê e converte um arquivo JSON em objeto JavaScript
 * @param {string} fileName Nome do arquivo JSON
 * @returns {Array<object>}
 */
function readJsonAsset(fileName) {
  const filePath = resolveAssetPath(fileName);
  const rawContent = fs.readFileSync(filePath, 'utf-8');
  try {

    
    const data = JSON.parse(rawContent);
    console.log(`[loadAssets] Carregado "${fileName}" com sucesso (${Array.isArray(data) ? data.length : 1} registros).`);
    return data;
  } catch (error) {
    throw new Error(`Erro ao fazer parse do JSON em "${filePath}": ${error.message}`);
  }
}

/**
 * Carrega todos os assets necessários para o domínio de controle de frequência
 * @returns {{
 *   professores: Array<object>,
 *   disciplinas: Array<object>,
 *   estudantes: Array<object>,
 *   chamadas: Array<object>
 * }}
 */
function loadAllAssets() {
  console.log('[loadAssets] Iniciando leitura dos arquivos de dados em scripts/assets...');

  const professores = readJsonAsset('professores.json');
  const disciplinas = readJsonAsset('disciplinas.json');
  const estudantes = readJsonAsset('estudantes.json');
  const chamadas = readJsonAsset('chamadas.json');

  return {
    professores,
    disciplinas,
    estudantes,
    chamadas,
  };
}

module.exports = {
  resolveAssetPath,
  readJsonAsset,
  loadAllAssets,
};
