package dev.cameloasa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.cameloasa.db.DatabaseInitializer;
import dev.cameloasa.web.PersonWebController;
import dev.cameloasa.web.TodoItemTaskWebController;
import dev.cameloasa.web.TodoItemWebController;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class WebApp {
  public static void main(String[] args) {

    DatabaseInitializer.initialize();

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    Javalin app =
        Javalin.create(
                config -> {
                  config.staticFiles.add("/public");
                  config.jsonMapper(new JavalinJackson(mapper));
                })
            .start(7000);

    app.exception(
        Exception.class,
        (e, ctx) -> {
          e.printStackTrace(); // afișează eroarea în consolă
          ctx.status(500).result("Internal Server Error: " + e.getMessage());
        });

    // openapi.json din resources
    app.get(
        "/openapi",
        ctx -> {
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
