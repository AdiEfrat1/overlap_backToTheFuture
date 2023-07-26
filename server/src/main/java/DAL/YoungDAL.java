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

    public String getAll() throws Exception {
        try {
            MongoCursor<Document> cursor = this.getCollection().find().iterator();
            JsonArray jsonArray = new JsonArray();

            while (cursor.hasNext()) {
                Document document = cursor.next();
                jsonArray.add(JsonParser.parseString(
                        this.replaceIdName(document.toJson(), "_id", "id")));
            }

            return jsonArray.toString();
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public String getById(int id) throws Exception {
        try {
            Document doc = this.getCollection().find(eq("_id", id)).first();
            if (doc != null) {
                return this.replaceIdName(doc.toJson(), "_id", "id");
            } else {
                throw new Exception("Could not find the required document in database");
            }
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public void removeById(int id) throws Exception {
        try {
            this.getCollection().deleteOne(eq("_id", id));
        } catch (Exception e) {
            throw new Exception("Could not remove young from Database");
        }
    }

    public void addYoung(Young young) throws Exception {
        try {
            Document newDoc = Document
                    .parse(this.replaceIdName(gson.toJson(young), "id", "_id"));

            this.getCollection().insertOne(newDoc);
        } catch (Exception e) {
            throw new Exception("Could not add young to Database");
        }
    }

    private String replaceIdName(String jsonString, String oldName, String newName) {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        int oldValue = jsonObject.get(oldName).getAsInt();
        jsonObject.remove(oldName);
        jsonObject.addProperty(newName, oldValue);

        return jsonObject.toString();
    }

    private MongoCollection<Document> getCollection() {
        MongoDatabase database = this.MONGO_CLIENT.getDatabase("local");

        return database.getCollection("youngs");

    }
}
