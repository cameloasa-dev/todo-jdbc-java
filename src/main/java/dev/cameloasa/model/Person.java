package dev.cameloasa.model;

public class Person {
  private int person_id;
  private String first_name;
  private String last_name;

  // Default constructor
  public Person() {}

  public Person(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;
  }

  // Overloaded constructor
  public Person(int person_id, String first_name, String last_name) {
    this.person_id = person_id;
    this.first_name = first_name;
    this.last_name = last_name;
  }

  // Getters
  public int getPerson_id() {
    return person_id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  // Setters
  public void setId(int id) {
    this.person_id = id;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  // toString

  @Override
  public String toString() {
    return "Person{"
        + "person_id="
        + person_id
        + ", first_name='"
        + first_name
        + '\''
        + ", last_name='"
        + last_name
        + '\''
        + '}';
  }
}
