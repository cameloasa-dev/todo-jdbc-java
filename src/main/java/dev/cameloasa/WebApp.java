package dev.cameloasa;

import dev.cameloasa.db.DatabaseInitializer;
import dev.cameloasa.web.PersonWebController;
import dev.cameloasa.web.TodoItemTaskWebController;
import dev.cameloasa.web.TodoItemWebController;
import io.javalin.Javalin;

public class WebApp {
    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public"); // servește swagger.html automat
        }).start(7000);

        // openapi.json din resources
        app.get("/openapi", ctx -> {
            ctx.contentType("application/json; charset=utf-8");
            ctx.result(WebApp.class.getResourceAsStream("/openapi.json"));
        });

        // redirect către swagger
        app.get("/docs", ctx -> ctx.redirect("/public/swagger.html"));

        // API routes
        PersonWebController.registerRoutes(app);
        TodoItemWebController.registerRoutes(app);
        TodoItemTaskWebController.registerRoutes(app);
    }
}
