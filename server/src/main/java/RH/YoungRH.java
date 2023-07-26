package RH;

import BL.YoungBL;
import Models.Young;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static spark.Spark.*;

public class YoungRH {

    private YoungBL youngBL = new YoungBL();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger logger = Logger.getLogger("MyLogger");


    public void setRoutes () {

        path("/youngs", () -> {
            post("/add", this::addYoung);
            delete("/remove/:id", this::removeYoung);
            get("", this::getAllYoungs);
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
            logger.severe("Failed to handle request to add young to DB");

            return false;
        }
    }

    private boolean removeYoung(Request req, Response res) {
        try {
            this.youngBL.removeYoungById(Integer.parseInt(req.params("id")));

            return true;
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to remove young");
            logger.severe(
                    "Failed to handle request to remove young { id: "
                            + req.params("id")
                            + " } from DB");


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
            logger.severe("Failed to handle request to get all youngs from DB");

            return null;
        }
    }

    private String getSpecificYoung(Request req, Response res) {
        res.type("application/json");


        try {
            Young young = this.youngBL
                    .getYoungById(Integer.parseInt(req.params("id")));

            return this.gson.toJson(young);
        } catch (NoSuchElementException e) {
            res.status(404);
            res.body("Resource not found");
        } catch (Exception e) {
            res.status(500);
            res.body("Error when trying to fetch specific young");

            logger.info(
                    "Failed to handle request to get young { id: "
                            + req.params("id")
                            + " } from DB");
        }

        return null;
    }
}
