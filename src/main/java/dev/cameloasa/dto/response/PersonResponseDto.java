package dev.cameloasa.dto.response;

public class PersonResponseDto {
  public int personId;
  public String firstName;
  public String lastName;

  public PersonResponseDto(int personId, String firstName, String lastName) {
    this.personId = personId;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
