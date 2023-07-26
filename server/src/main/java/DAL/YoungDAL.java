package DAL;

import DB.YoungDB;
import Models.Young;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;

public class YoungDAL {

    private final String DB_PATH = "C:/Adi Overlap/overlap_backToTheFuture/server/src/main/java/DB/youngs.json";

    private final String CONNECTION_URI = "mongodb://localhost:27017/?retryWrites=true&serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&3t.uriVersion=3&3t.connection.name=Local+-+imported+on+18+Jul+2023&3t.alwaysShowAuthDB=true&3t.alwaysShowDBFromUserRole=true";

    private YoungDB youngDB = new YoungDB();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getAllYoungs() throws Exception {
        try (MongoClient mongoClient = MongoClients.create(this.CONNECTION_URI)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("youngs");

            MongoCursor<Document> cursor = collection.find().iterator();

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

    public String getSpecific(int id) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(this.CONNECTION_URI)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("youngs");
            Document doc = collection.find(eq("_id", id)).first();
            if (doc != null) {
                return this.replaceIdName(doc.toJson(), "_id", "id");
            } else {
                throw new Exception("Could not find the required document in database");
            }
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public void removeYoung(int id) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(this.CONNECTION_URI)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("youngs");

            collection.deleteOne(eq("_id", id));
        } catch (Exception e) {
            throw new Exception("Could not remove young from Database");
        }
    }

    private JsonArray readJsonArrayFromFile() {
        return null;
    }

    public void addYoung(Young young) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(this.CONNECTION_URI)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("youngs");

            Document newDoc = Document
                    .parse(this.replaceIdName(gson.toJson(young), "id", "_id"));

            collection.insertOne(newDoc);
        } catch (Exception e) {
            throw new Exception("Could not add young to Database");
        }
    }

    private ArrayList<Young> jsonToYoungArrayList(JsonArray jsonArray) {
        Type listType = new TypeToken<ArrayList<Young>>(){}.getType();

        return this.gson.fromJson(jsonArray, listType);
    }

    private String replaceIdName(String jsonString, String oldName, String newName) {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        int oldValue = jsonObject.get(oldName).getAsInt();
        jsonObject.remove(oldName);
        jsonObject.addProperty(newName, oldValue);

        return jsonObject.toString();
    }
}
