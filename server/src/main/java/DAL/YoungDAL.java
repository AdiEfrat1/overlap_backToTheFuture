package DAL;

import DB.YoungDB;
import Models.Young;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

    public ArrayList<Young> getAllYoungs() throws FileNotFoundException {
        JsonArray jsonArray = this.readJsonArrayFromFile();

        return this.jsonToYoungArrayList(jsonArray);
    }

    public String getSpecific(int id) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(this.CONNECTION_URI)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("youngs");
            Document doc = collection.find(eq("_id", id)).first();
            if (doc != null) {
                return doc.toJson();
            } else {
                throw new Exception("Could not find the required document in database");
            }
        } catch (Exception e) {
            throw new Exception("Connection to database failed");
        }
    }

    public void removeYoung(int id) throws FileNotFoundException {
        JsonArray jsonArray = this.readJsonArrayFromFile();
        ArrayList<Young> youngs = this.jsonToYoungArrayList(jsonArray);
        youngs.removeIf((young) -> young.id() == id);

        try (FileWriter fileWriter = new FileWriter(this.DB_PATH)) {
            fileWriter.write(this.gson.toJson(youngs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addYoung(Young young) throws FileNotFoundException {
        JsonArray jsonArray = this.readJsonArrayFromFile();
        JsonObject newElement = this.gson.toJsonTree(young).getAsJsonObject();
        jsonArray.add(newElement);

        try (FileWriter fileWriter = new FileWriter(this.DB_PATH)) {
            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Young> jsonToYoungArrayList(JsonArray jsonArray) {
        Type listType = new TypeToken<ArrayList<Young>>(){}.getType();

        return this.gson.fromJson(jsonArray, listType);
    }

    private JsonArray readJsonArrayFromFile() throws FileNotFoundException {
        JsonElement jsonElement = new Gson().fromJson(new FileReader(this.DB_PATH), JsonElement.class);

        return jsonElement.getAsJsonArray();
    }
}
