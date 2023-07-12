package RH;

import BL.YoungBL;
import DAL.Young;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.NoSuchElementException;

import static spark.Spark.*;

public class YoungRH {

    private YoungBL youngBL = new YoungBL();

    public void rh () {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        path("/young", () -> {
            post("/add/:id", (req, res) -> null);
            delete("/remove/:id", (req, res) -> null);
            get("/all", (req, res) -> gson.toJson(this.youngBL.getAllYoung()));
            get("/fullDetails/:id", (req, res) -> {
                try {
                    Young young = this.youngBL.getSpecificFullDetails(Integer.parseInt(req.params("id")));

                    return gson.toJson(young);
                } catch (NoSuchElementException e) {
                    res.status(404);
                    res.body("Resource not found");
                }

                return null;
            });
        });
    }
}
