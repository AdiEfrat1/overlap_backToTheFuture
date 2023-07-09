import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/hello", (req, res) -> {
            res.redirect("hello.html");
            return null;
        });

        get("/world", (req, res) -> "World");

        post("/mister", (request, response) -> "Mr." + request.body());
    }
}