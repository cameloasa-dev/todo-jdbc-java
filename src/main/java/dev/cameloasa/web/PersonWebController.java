package dev.cameloasa.web;

import dev.cameloasa.controller.PersonController;
import dev.cameloasa.daoimpl.PersonDaoImpl;
import dev.cameloasa.service.PersonService;
import io.javalin.Javalin;

public class PersonWebController {

  public static void registerRoutes(Javalin app) {

    PersonController controller = new PersonController(new PersonService(new PersonDaoImpl()));

    // GET /person/{id}
    app.get(
        "/person/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          ctx.json(controller.getPersonById(id));
        });

    // GET /person
    app.get(
        "/person",
        ctx -> {
          ctx.json(controller.getAllPeople());
        });

    // POST /person
    app.post(
        "/person",
        ctx -> {
          String firstName = ctx.formParam("firstName");
          String lastName = ctx.formParam("lastName");

          ctx.json(controller.createPerson(firstName, lastName));
        });

    // PUT /person/{id}
    app.put(
        "/person/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          String firstName = ctx.formParam("firstName");
          String lastName = ctx.formParam("lastName");

          ctx.json(controller.updatePerson(id, firstName, lastName));
        });

    // DELETE /person/{id}
    app.delete(
        "/person/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          ctx.json(controller.deletePerson(id));
        });
  }
}
