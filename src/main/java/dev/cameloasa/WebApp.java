package dev.cameloasa;

import dev.cameloasa.web.PersonWebController;
import io.javalin.Javalin;

public class WebApp {
  public static void main(String[] args) {

    Javalin app = Javalin.create().start(7000);

    PersonWebController.registerRoutes(app);
  }
}
