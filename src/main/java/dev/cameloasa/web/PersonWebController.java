package dev.cameloasa.web;

import dev.cameloasa.controller.PersonController;
import dev.cameloasa.daoimpl.PersonDaoImpl;
import dev.cameloasa.dto.request.PersonRequestDto;
import dev.cameloasa.dto.response.PersonResponseDto;
import dev.cameloasa.model.Person;
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
          Person person = controller.getPersonById(id);

          PersonResponseDto response =
              new PersonResponseDto(
                  person.getPersonId(), person.getFirstName(), person.getLastName());

          ctx.json(response);
        });

    // GET /person
    app.get(
        "/person",
        ctx -> {
          var people = controller.getAllPeople();

          var responseList =
              people.stream()
                  .map(
                      p ->
                          new PersonResponseDto(p.getPersonId(), p.getFirstName(), p.getLastName()))
                  .toList();

          ctx.json(responseList);
        });

    // POST /person
    app.post(
        "/person",
        ctx -> {
          PersonRequestDto dto = ctx.bodyAsClass(PersonRequestDto.class);

          Person created = controller.createPerson(dto.firstName, dto.lastName);

          PersonResponseDto response =
              new PersonResponseDto(
                  created.getPersonId(), created.getFirstName(), created.getLastName());

          ctx.json(response);
        });

    // PATCH /person/{id}
    app.patch(
        "/person/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          PersonRequestDto dto = ctx.bodyAsClass(PersonRequestDto.class);

          boolean updated = controller.updatePerson(id, dto.firstName, dto.lastName);

          ctx.json(updated);
        });

    // DELETE /person/{id}
    app.delete(
        "/person/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          boolean deleted = controller.deletePerson(id);
          ctx.json(deleted);
        });
  }
}
