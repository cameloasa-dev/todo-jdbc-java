# ToDo App – Java JDBC Project

This project is a Java OOP implementation of a simple To‑Do management system.
It demonstrates object‑oriented design, relationships between classes, encapsulation, and basic business logic.

The project is part of a collection of older Java exercises currently being updated and prepared for future improvements and migration into a GitHub organization.

## 🎯 Project Purpose

This project focuses on practicing:

- Object‑oriented programming

- Class relationships (Person → TodoItem → TodoItemTask)

- Encapsulation and validation

- Working with dates (LocalDate)

- Basic business logic

- Clean code structure

- JUnit 5 testing

- Code formatting and linting (Spotless + Checkstyle)

## 📦 Current Features

**Person**.Represents a user with:

```json
id
first name
last name
email
```

**TodoItem**.Represents a task with:

```json
id
title
description
deadline (LocalDate)
done status
method isOverdue()
```

**TodoItemTask**.Represents the assignment of a TodoItem to a Person:

```json
id
assigned status
assignee (Person)
todo item (TodoItem)
```

## 📁 Project Structure

```text
errors/
img/
scripts/
src/
  main/
    java/
      dev/cameloasa/
        App.java
        model/
          Person.java
          TodoItem.java 
        db/
          schema.sql
        dao/
          daoimpl/
            PersonDao.java
            PersonDaoImpl.java
            TodoItemDao.java 
            
  test/
    java/
      dev/cameloasa/
```

## 🧪 Running the Project

Compile:

```bash
mvn compile
```

Run:

```bash
mvn exec:java
```

Run tests:

```bash
mvn test
```

Full rebuild:

```bash
mvn clean install
```

## 🎨 Code Formatting & Linting

Format code (Spotless):

```bash
mvn spotless:apply
```

Check code style (Checkstyle):

```bash
mvn checkstyle:check
```
