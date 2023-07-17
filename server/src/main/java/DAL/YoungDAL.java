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
        JsonElement jsonElement = new Gson().fromJson(new FileReader(this.DB_PATH), JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        return jsonToYoungArrayList(jsonArray);
    }

    public Young getSpecific(int id) throws Exception {
        JsonElement jsonElement = new Gson().fromJson(new FileReader(this.DB_PATH), JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        return jsonToYoungArrayList(jsonArray)
                .stream()
                .filter((young) -> young.id() == id)
                .findFirst()
                .orElseThrow(Exception::new);
    }

    public void removeYoung(int id) throws FileNotFoundException {
        this.youngDB.youngs.removeIf((young) -> young.id() == id);
    }

    public void addYoung(Young young) throws FileNotFoundException {
        JsonElement jsonElement = new Gson().fromJson(new FileReader(this.DB_PATH), JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonObject newElement = gson.toJsonTree(young).getAsJsonObject();
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
}
