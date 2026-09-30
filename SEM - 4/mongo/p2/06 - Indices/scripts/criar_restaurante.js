const falso = require('@ngneat/falso');
const { MongoClient } = require('mongodb');

const uri = 'mongodb+srv://alangiovanepaes_db_user:fkLtUHo5ur2CZhGi@restaurants.wrosyo5.mongodb.net/?appName=restaurants'; // Ajuste sua URI de conexão aqui se necessário

const client = new MongoClient(uri);
function getRandomItem(arr) {
  return arr[Math.floor(Math.random() * arr.length)];
}

function getRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

async function populateCollection(dbName, colName, totalRecords, generatorFn) {
  const db = client.db(dbName);
  const collection = db.collection(colName);

  // Verifica se a coleção já possui documentos
  const count = await collection.countDocuments();
  if (count > 0) {
    console.log(`[SKIP] A coleção ${dbName}.${colName} já possui ${count} registos. A saltar...`);
    return;
  }

  console.log(`[START] A gerar dados para ${dbName}.${colName}...`);
  const batchSize = 10000;

  for (let batch = 0; batch < totalRecords / batchSize; batch++) {
    const data = [];
    for (let i = 0; i < batchSize; i++) {
      data.push(generatorFn(batch * batchSize + i));
    }
    await collection.insertMany(data);
    console.log(`- ${dbName}.${colName}: ${Math.min((batch + 1) * batchSize, totalRecords)} de ${totalRecords} inseridos.`);
  }

  console.log(`[DONE] ${dbName}.${colName} populada com sucesso!\n`);
}

async function run() {
  try {
    await client.connect();
    console.log('Ligação ao MongoDB estabelecida com sucesso!\n');

    // 1. sample_restaurants.restaurants (Questões 1, 7 e 8)
    const boroughs = ['Manhattan', 'Brooklyn', 'Queens', 'Bronx', 'Staten Island'];
    const cuisines = ['Steak', 'American', 'Italian', 'Mexican', 'Chinese', 'Japanese', 'Bakery'];
    const gradesList = ['A', 'B', 'C'];

    await populateCollection('sample_restaurants', 'restaurants', 100000, () => ({
      name: falso.randCompanyName(),
      borough: getRandomItem(boroughs),
      cuisine: getRandomItem(cuisines),
      restaurant_id: getRandomNumber(10000000, 99999999).toString(),
      address: {
        building: getRandomNumber(1, 9999).toString(),
        street: falso.randStreetName(),
        zipcode: falso.randZipCode(),
        coord: [falso.randLongitude(), falso.randLatitude()]
      },
      grades: [
        { date: falso.randPastDate(), grade: getRandomItem(gradesList), score: getRandomNumber(0, 140) },
        { date: falso.randPastDate(), grade: getRandomItem(gradesList), score: getRandomNumber(0, 140) }
      ]
    }));

    // 2. sample_analytics.customers (Questões 2 e 3)
    await populateCollection('sample_analytics', 'customers', 50000, (index) => {
      // Injeta propositadamente o email exato do exercício na primeira posição
      const email = index === 0 
        ? 'cooperalexis@hotmail.com' 
        : (Math.random() > 0.5 ? falso.randUserName() + '@gmail.com' : falso.randEmail());

      return {
        username: falso.randUserName(),
        name: falso.randFullName(),
        address: falso.randStreetAddress(),
        birthdate: falso.randPastDate(),
        email: email,
        accounts: [getRandomNumber(100000, 999999)]
      };
    });

    // 3. sample_airbnb.listingsAndReviews (Questão 4)
    const roomTypes = ['Entire home/apt', 'Private room', 'Shared room'];
    await populateCollection('sample_airbnb', 'listingsAndReviews', 50000, () => ({
      name: falso.randProductDescription(),
      price: getRandomNumber(50, 25000),
      beds: getRandomNumber(1, 16),
      room_type: getRandomItem(roomTypes),
      accommodates: getRandomNumber(1, 16),
      bathrooms: getRandomNumber(1, 5)
    }));

    // 4. sample_mflix.movies (Questões 5 e 6)
    const ratings = [4.5, 5.0, 5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5];
    await populateCollection('sample_mflix', 'movies', 50000, (index) => {
      // Gera alguns títulos longos (> 100 carateres) para testar a expressão da Questão 5
      const title = (index % 10 === 0)
        ? falso.randCatchPhrase() + ' - ' + falso.randParagraph().substring(0, 110)
        : falso.randCatchPhrase();

      return {
        title: title,
        year: getRandomNumber(1950, 2024),
        imdb: {
          rating: getRandomItem(ratings),
          votes: getRandomNumber(10, 50000)
        },
        awards: {
          wins: getRandomNumber(0, 10),
          nominations: getRandomNumber(0, 20),
          text: 'Won awards'
        }
      };
    });

    console.log('Processo de povoamento concluído com êxito!');
  } catch (error) {
    console.error('Erro durante a execução:', error);
  } finally {
    await client.close();
  }
}

run();