package DAL;

import DB.YoungDB;
import Models.Young;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class YoungDAL {

    private final String DB_PATH = "C:/Adi Overlap/overlap_backToTheFuture/server/src/main/java/DB/youngs.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoungs() throws FileNotFoundException {
        JsonArray jsonArray = this.readJsonArrayFromFile();

        return this.jsonToYoungArrayList(jsonArray);
    }

    public Young getSpecific(int id) throws Exception {
        JsonArray jsonArray = this.readJsonArrayFromFile();

        return this.jsonToYoungArrayList(jsonArray)
                .stream()
                .filter((young) -> young.id() == id)
                .findFirst()
                .orElseThrow(Exception::new);
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
