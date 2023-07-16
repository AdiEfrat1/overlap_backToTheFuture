package RH;

import BL.YoungBL;
import DAL.Young;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.NoSuchElementException;

import static spark.Spark.*;

public class YoungRH {

    private YoungBL youngBL = new YoungBL();


    public void setRoutes () {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        path("/youngs", () -> {
            post("/add", (req, res) -> {
                try {
                    this.youngBL.addYoung(gson.fromJson(req.body(), Young.class));
                } catch (Exception e) {
                    res.status(500);
                    res.body("Error when trying to add young");
                }

                return null;
            });
            delete("/remove/:id", (req, res) -> {
                try {
                    this.youngBL.removeYoung(Integer.parseInt(req.params("id")));
                } catch (Exception e) {
                    res.status(500);
                    res.body("Error when trying to remove young");
                }

                return null;
            });
            get("/all", (req, res) -> gson.toJson(this.youngBL.getAllYoung()));
            get("/:id", (req, res) -> {
                try {
                    Young young = this.youngBL
                            .getSpecific(Integer.parseInt(req.params("id")));

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
