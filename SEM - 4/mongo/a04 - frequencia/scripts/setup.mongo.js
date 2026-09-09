// =============================================================================
// SETUP DO BANCO DE DADOS — MONGO SHELL (mongosh / mongo)
// Banco: frequenciaDb
// Executa a criação de coleções, índices e carga inicial de dados
// =============================================================================

const dbName = 'frequenciaDb';
db = db.getSiblingDB(dbName);

print("=============================================================================");
print(" INICIANDO SETUP NO MONGODB SHELL");
print(" Banco de Dados: " + db.getName());
print("=============================================================================\n");

// 1. Carregador flexível de arquivos JSON (compatível com mongosh e legado mongo shell)
let fs = null;
try {
  fs = require('fs');
} catch (e) {
  // Se require não estiver disponível, utilizará cat()
}

function carregarJson(nomeArquivo) {
  const caminhos = [
    'scripts/assets/controle-frequencia/' + nomeArquivo,
    './scripts/assets/controle-frequencia/' + nomeArquivo,
    'assets/controle-frequencia/' + nomeArquivo,
    '../scripts/assets/controle-frequencia/' + nomeArquivo,
  ];

  if (fs) {
    for (const c of caminhos) {
      if (fs.existsSync(c)) {
        print("[setup] Carregando: " + c);
        return JSON.parse(fs.readFileSync(c, 'utf-8'));
      }
    }
  }

  if (typeof cat === 'function') {
    for (const c of caminhos) {
      try {
        const conteudo = cat(c);
        if (conteudo && conteudo.trim().length > 0) {
          print("[setup] Carregando via cat(): " + c);
          return JSON.parse(conteudo);
        }
      } catch (err) {}
    }
  }

  throw new Error("Não foi possível carregar o arquivo: " + nomeArquivo);
}

// 2. Criação / Recriação das coleções
print("\n[Passo 1/4] Recriando coleções...");
const colecoes = ['professores', 'disciplinas', 'estudantes', 'chamadas'];
for (const col of colecoes) {
  db.getCollection(col).drop();
  db.createCollection(col);
  print("✓ Coleção '" + col + "' recriada.");
}

// 3. Criação de índices
print("\n[Passo 2/4] Criando índices otimizados...");

// Professores
db.professores.createIndex({ RP: 1 }, { unique: true });

// Disciplinas
db.disciplinas.createIndex({ codigo: 1 }, { unique: true });
db.disciplinas.createIndex({ "turmas.codigo": 1 });

// Estudantes
db.estudantes.createIndex({ RA: 1 }, { unique: true });
db.estudantes.createIndex({ "matriculas.disciplinaCodigo": 1 });

// Chamadas
db.chamadas.createIndex({ data: 1 });
db.chamadas.createIndex({ "disciplina.codigo": 1, "turma.codigo": 1 });
db.chamadas.createIndex({ "docente.RP": 1 });
db.chamadas.createIndex({ "alunos.RA": 1 });
print("✓ Índices criados com sucesso.");

// 4. Inserção de dados
print("\n[Passo 3/4] Inserindo dados iniciais...");
const professoresData = carregarJson('professores.json');
const disciplinasData = carregarJson('disciplinas.json');
const estudantesData = carregarJson('estudantes.json');
const chamadasData = carregarJson('chamadas.json');

if (professoresData && professoresData.length) {
  db.professores.insertMany(professoresData);
  print("✓ Professores inseridos: " + professoresData.length);
}

if (disciplinasData && disciplinasData.length) {
  db.disciplinas.insertMany(disciplinasData);
  print("✓ Disciplinas inseridas: " + disciplinasData.length);
}

if (estudantesData && estudantesData.length) {
  db.estudantes.insertMany(estudantesData);
  print("✓ Estudantes inseridos: " + estudantesData.length);
}

if (chamadasData && chamadasData.length) {
  db.chamadas.insertMany(chamadasData);
  print("✓ Chamadas inseridas: " + chamadasData.length);
}

// 5. Resumo e validação
print("\n[Passo 4/4] Validando dados inseridos no banco '" + db.getName() + "':");
print("-------------------------------------------------------------");
print(" Total de Professores : " + db.professores.countDocuments());
print(" Total de Disciplinas : " + db.disciplinas.countDocuments());
print(" Total de Estudantes  : " + db.estudantes.countDocuments());
print(" Total de Chamadas    : " + db.chamadas.countDocuments());
print("-------------------------------------------------------------");

print("\n=============================================================================");
print(" [OK] SETUP DO MONGODB CONCLUÍDO COM SUCESSO VIA MONGO SHELL!");
print("=============================================================================\n");
