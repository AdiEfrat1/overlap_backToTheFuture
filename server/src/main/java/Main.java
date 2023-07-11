import DB.YoungDB;
import RH.YoungRH;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        YoungDB youngDB = new YoungDB();

        get("/", (req, res) -> {
            res.redirect("Main.html");
            return null;
        });

        YoungRH youngRH = new YoungRH();
        youngRH.rh();
    }
}