package dialog;
import io.javalin.Javalin;

/**
 * This class defines and hosts a REST API server for the Dialog Server, for use by the 
 * frontend toolkit allowing modification of the dialog interaction parameters.
*/
public class ToolkitAPI {
    private static final int PORT = 7000;

    public ToolkitAPI() {
        Javalin app = Javalin.create().start(PORT);
        System.out.println("API started and listening on " + PORT);
        app.get("/api/hello", ctx -> ctx.result("Hello World"));
        app.get("/api/hello/:name", ctx -> {
            String name = ctx.pathParam("name");
            ctx.result("Hello " + name);
        });
        app.post("/api/hello", ctx -> {
            String name = ctx.body();
            ctx.result("Hello " + name);
        });
    }
}