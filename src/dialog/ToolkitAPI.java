package dialog;
import com.google.gson.Gson;

import io.javalin.Javalin;

/**
 * This class defines and hosts a REST API server for the Dialog Server, for use by the 
 * frontend toolkit allowing modification of the dialog interaction parameters.
*/
public class ToolkitAPI {
    private static final int PORT = 62753;
    DialogStateModel stateModel;
    Gson gson;

    public ToolkitAPI(DialogStateModel stateModel) {
        this.gson = new Gson();
        this.stateModel = stateModel;
        Javalin app = Javalin.create().start(PORT);

        // endpoints
        app.get("/hello", ctx -> ctx.result("Hello World!"));
        app.put("/api/params", ctx -> {
            String json = ctx.body();
            ctx.result("Hello you sent me: " + json);
            ToolkitState state = gson.fromJson(json, ToolkitState.class);
            stateModel.setToolkitState(state);
        });
    }
}