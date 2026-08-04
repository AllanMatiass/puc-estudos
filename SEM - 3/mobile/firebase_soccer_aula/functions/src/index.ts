import {initializeApp} from 'firebase-admin/app';
import {FieldValue, getFirestore, Timestamp} from 'firebase-admin/firestore';
import {setGlobalOptions} from 'firebase-functions';
import {onRequest} from 'firebase-functions/https';
import * as logger from 'firebase-functions/logger';

initializeApp();
const db = getFirestore();
const soccerTeamsCollection = db.collection('soccerTeams');

setGlobalOptions({maxInstances: 10});

type SoccerTeam = {
    name: string;
    foundationYear: number;
    createdAt: Timestamp
}

export const addNewSoccerTeam = onRequest(async (request, response) => {
  const data: SoccerTeam = request.body;
  if (!data.name || !data.foundationYear) {
    response.status(400).send({
      error: 'name or foundationYEar missing in body',
    });
    return;
  }

  if (data.foundationYear > Date.now() || data.foundationYear < 1860) {
    response.status(400).send({
      error: 'Invalid foundation year',
    });
    return;
  }

  const doc = await soccerTeamsCollection.add({
    ...data,
    createdAt: FieldValue.serverTimestamp,
  });

  logger.info(`new team created: ${doc.id, data.name, data.foundationYear}`);


  response.send({
    message: 'Team created successfully',
    id: doc.id,
  });
});

export const getSoccerTeams = onRequest(async (request, response) => {
  const snapshot = await soccerTeamsCollection.
    orderBy('foundationYear', 'asc').get();

  const teams = snapshot.docs.map((t) => ({
    id: t.id,
    name: t.data().name,
    foundationYear: t.data().foundationYear,
  }));

  logger.info('List viewed, length:', teams.length);
  response.json({teams});
});
