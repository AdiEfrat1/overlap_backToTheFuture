package ControllerLayer;

import static spark.Spark.*;

public class YoungController {

    public static void controller () {
        path("/young", () -> {
            post("/add/:id", (req, res) -> null);
            delete("/remove/:id", (req, res) -> null);
            get("/all", (req, res) -> null);
            get("/fullDetails/:id", (req, res) -> null);
        });
    }
}
