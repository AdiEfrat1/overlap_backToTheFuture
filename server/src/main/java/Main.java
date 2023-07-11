import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/", (req, res) -> {
            res.redirect("Main.html");
            return null;
        });

        path("/young", () -> {

        });
    }
}