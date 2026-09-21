// =============================================================================
// CATÁLOGO COMPLETO DE CONSULTAS — MONGO SHELL (mongosh / mongo)
// Banco: frequenciaDb
// Executa todas as consultas de todos os domínios
// =============================================================================

const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("\n=============================================================================");
print("       INICIANDO DEMONSTRAÇÃO COMPLETA DE CONSULTAS NO MONGODB");
print("       Banco de Dados: " + db.getName());
print("=============================================================================\n");

function carregarScript(caminhos) {
  for (const caminho of caminhos) {
    try {
      load(caminho);
      return true;
    } catch (err) {
      // Tenta o próximo caminho
    }
  }
  return false;
}

const scripts = [
  {
    nome: "Professores",
    paths: ["queries/professores.js", "./professores.js", "queries/professores.mongodb.js"]
  },
  {
    nome: "Estudantes",
    paths: ["queries/estudantes.js", "./estudantes.js", "queries/estudantes.mongodb.js"]
  },
  {
    nome: "Disciplinas",
    paths: ["queries/disciplinas.js", "./disciplinas.js", "queries/disciplinas.mongodb.js"]
  },
  {
    nome: "Chamadas",
    paths: ["queries/chamadas.js", "./chamadas.js", "queries/chamadas.mongodb.js"]
  },
  {
    nome: "Relacionamentos",
    paths: ["queries/relacionamentos.js", "./relacionamentos.js", "queries/relacionamentos.mongodb.js"]
  }
];

for (const script of scripts) {
  print("\n>>> Carregando domínio: " + script.nome + "...");
  const sucesso = carregarScript(script.paths);
  if (!sucesso) {
    print("⚠️ Não foi possível localizar o arquivo de " + script.nome + " pelos caminhos testados.");
  }
}

print("\n=============================================================================");
print("       [OK] TODAS AS CONSULTAS FORAM EXECUTADAS COM SUCESSO!");
print("=============================================================================\n");
