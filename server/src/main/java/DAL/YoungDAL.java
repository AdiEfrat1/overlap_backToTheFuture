package DAL;

import DB.YoungDB;
import Models.Young;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class YoungDAL {

    private final String DB_PATH = "C:/Adi Overlap/overlap_backToTheFuture/server/src/main/java/DB/youngs.json";

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoungs() throws FileNotFoundException {
        JsonElement jsonElement = new Gson().fromJson(new FileReader(this.DB_PATH), JsonElement.class);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        ArrayList<Young> youngs = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();

            Young currYoung = new Young(
                    jsonObject.get("id").getAsInt(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("city").getAsString(),
                    jsonObject.get("phone").getAsString(),
                    jsonObject.get("hobby").getAsString(),
                    jsonObject.get("book").getAsString());

            youngs.add(currYoung);
        }

        return youngs;
    }

    public Young getSpecific(int id) {
        return this.youngDB.youngs.stream()
                .filter((young) -> young.id() == id).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public void removeYoung(int id) {
        this.youngDB.youngs.removeIf((young) -> young.id() == id);
    }

    public void addYoung(Young young) {
        this.youngDB.youngs.add(young);
    }
}
