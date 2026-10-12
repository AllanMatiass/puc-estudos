package br.com.geo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoService implements AutoCloseable {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> pontosCollection;
    private final MongoCollection<Document> countersCollection;

    public MongoService() throws Exception {
        // Reduz ruídos de log do driver MongoDB no console
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        String uri = loadMongoUri();
        this.mongoClient = MongoClients.create(uri);
        this.database = mongoClient.getDatabase("geo_db");
        this.pontosCollection = database.getCollection("pontos");
        this.countersCollection = database.getCollection("counters");
    }

    private static String loadMongoUri() throws Exception {
        String uri = System.getenv("MONGODB_URI");
        if (uri != null && !uri.isBlank()) {
            return uri;
        }

        throw new Exception("URI não configurada");
    }

    private synchronized int proximoId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "ponto_id"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
        );

        int seq = (counter != null && counter.getInteger("seq") != null) ? counter.getInteger("seq") : 1;

        // Caso já existam pontos com ID maior cadastrados anteriormente
        Document maiorPonto = pontosCollection.find().sort(Sorts.descending("id")).first();
        if (maiorPonto != null && maiorPonto.getInteger("id") != null) {
            int maiorIdExistente = maiorPonto.getInteger("id");
            if (seq <= maiorIdExistente) {
                seq = maiorIdExistente + 1;
                countersCollection.updateOne(
                        Filters.eq("_id", "ponto_id"),
                        Updates.set("seq", seq)
                );
            }
        }

        return seq;
    }



    public Ponto cadastrarPonto(String nome, double latitude, double longitude) {
        int id = proximoId();

        Document localizacao = new Document("type", "Point")
                .append("coordinates", Arrays.asList(longitude, latitude));

        Document doc = new Document("id", id)
                .append("nome", nome)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .append("localizacao", localizacao);

        pontosCollection.insertOne(doc);

        return new Ponto(id, nome, latitude, longitude);
    }

    public Ponto findById(int id) {
        Document doc = pontosCollection.find(Filters.eq("id", id)).first();
        if (doc == null) {
            return null;
        }

        Coordenadas coordenadas = this.getCoordenadas(doc);

        double lat = coordenadas.lat();
        double lon = coordenadas.lon();

        String nome = doc.getString("nome");

        return new Ponto(id, nome != null ? nome : "", lat, lon);
    }

    public List<Ponto> listarTodos() {
        List<Ponto> pontos = new ArrayList<>();

        for (Document doc : pontosCollection.find().sort(Sorts.ascending("id"))) {
            Integer id = doc.getInteger("id");
            if (id == null) continue;

            String nome = doc.getString("nome");
            Coordenadas coordenadas = this.getCoordenadas(doc);

            double lat = coordenadas.lat();
            double lon = coordenadas.lon();

            pontos.add(new Ponto(id, nome != null ? nome : "", lat, lon));
        }
        return pontos;
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    private Coordenadas getCoordenadas(Document doc){
        Double latNum = doc.get("latitude", Double.class);
        Double lonNum = doc.get("longitude", Double.class);

        double lat = latNum != null ? latNum : 0.0;
        double lon = lonNum != null ? lonNum : 0.0;

        return new Coordenadas(lat, lon);
    }

    public List<Ponto> buscarPontosProximos(
            Coordenadas coordenadas,
            double raioMetros
    ) {
        double longitude = coordenadas.lon();
        double latitude = coordenadas.lat();


        Point origem = new Point(
                new Position(longitude, latitude)
        );

        List<Ponto> pontos = new ArrayList<>();

        for (Document doc : this.pontosCollection.find(
                Filters.nearSphere(
                        "localizacao",
                        origem,
                        raioMetros,
                        null
                )
        )) {
            Integer id = doc.getInteger("id");
            if (id == null) {
                continue;
            }

            String nome = doc.getString("nome");
            Coordenadas coordenadasRet = getCoordenadas(doc);

            pontos.add(new Ponto(
                    id,
                    nome != null ? nome : "",
                    coordenadasRet.lat(),
                    coordenadasRet.lon()
            ));
        }

        return pontos;
    }
}
