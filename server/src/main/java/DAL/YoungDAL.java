package DAL;

import Models.Young;
import com.google.gson.*;
import com.mongodb.client.*;
import org.bson.Document;


import static com.mongodb.client.model.Filters.eq;

public class YoungDAL {
    private final String CONNECTION_URI = "mongodb://localhost:27017/?retryWrites=true&serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&3t.uriVersion=3&3t.connection.name=Local+-+imported+on+18+Jul+2023&3t.alwaysShowAuthDB=true&3t.alwaysShowDBFromUserRole=true";

    private final MongoClient MONGO_CLIENT = MongoClients.create(this.CONNECTION_URI);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Young[] getAll() throws Exception {
        try {
            MongoCursor<Document> cursor = this.getCollection().find().iterator();
            JsonArray jsonArray = new JsonArray();

            while (cursor.hasNext()) {
                Document document = cursor.next();
                jsonArray.add(JsonParser.parseString(document.toJson()));
            }

            return gson.fromJson(jsonArray.toString(), Young[].class);
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public Young getById(int id) throws Exception {
        try {
            Document doc = this.getCollection().find(eq("id", id)).first();
            if (doc != null) {
                return gson.fromJson(doc.toJson(), Young.class);
            } else {
                throw new Exception("Could not find the required document in database");
            }
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public void removeById(int id) throws Exception {
        try {
            this.getCollection().deleteOne(eq("id", id));
        } catch (Exception e) {
            throw new Exception("Could not remove young from Database");
        }
    }

    public void add(Young young) throws Exception {
        try {
            Document newDoc = Document
                    .parse(gson.toJson(young));

            this.getCollection().insertOne(newDoc);
        } catch (Exception e) {
            throw new Exception("Could not add young to Database");
        }
    }

    private MongoCollection<Document> getCollection() {
        MongoDatabase database = this.MONGO_CLIENT.getDatabase("local");

        return database.getCollection("youngs");

    }
}
