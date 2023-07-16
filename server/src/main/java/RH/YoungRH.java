package RH;

import BL.YoungBL;
import Models.Young;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.util.NoSuchElementException;

import static spark.Spark.*;

public class YoungRH {

    private YoungBL youngBL = new YoungBL();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void setRoutes () {

        path("/youngs", () -> {
            post("/add", this::addYoung);
            delete("/remove/:id", this::removeYoung);
            get("/all", this::getAllYoungs);
            get("/:id", this::getSpecificYoung);
        });
    }

    private boolean addYoung(Request req, Response res) {
        try {
            this.youngBL.addYoung(this.gson.fromJson(req.body(), Young.class));

            return true;
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to add young");

            return false;
        }
    }

    private boolean removeYoung(Request req, Response res) {
        try {
            this.youngBL.removeYoung(Integer.parseInt(req.params("id")));

            return true;
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to remove young");

            return false;
        }
    }

    private String getAllYoungs(Request req, Response res) {
        res.type("application/json");

        try {
            return this.gson.toJson(this.youngBL.getAllYoung());
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to fetch all youngs");

            return null;
        }
    }

    private String getSpecificYoung(Request req, Response res) {
        res.type("application/json");

        try {
            Young young = this.youngBL
                    .getSpecific(Integer.parseInt(req.params("id")));

            return this.gson.toJson(young);
        } catch (NoSuchElementException e) {
            res.status(404);
            res.body("Resource not found");
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to fetch specific young");
        }

        return null;
    }
}
