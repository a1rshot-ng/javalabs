package org.airng.labthird.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.airng.labthird.base.Person;
import org.airng.labthird.base.Student;
import org.airng.labthird.base.Teacher;

import java.io.IOException;
import java.util.*;

public class CachedPeopleDao implements Dao<Person> {
  private final Map<UUID, Person> map;

  @Override
  public Optional<Person> get(UUID id) {
    return Optional.ofNullable(map.get(id));
  }

  @Override
  public List<Person> listAll() {
    return map.values().stream().toList();
  }

  @Override
  public void save(Person person) {
    map.put(person.getId(), person);
  }

  @Override
  public void delete(UUID id) throws IOException {
    if (map.remove(id) == null) throw new IOException();
  }

  @JsonCreator
  public CachedPeopleDao(@JsonProperty("students") Map<String, Student> students, @JsonProperty("teachers") Map<String, Teacher> teachers) {
    this.map = new HashMap<>();
    for (Person person: students.values()) {
      this.map.put(person.getId(), person);
    }
    for (Person person: teachers.values()) {
      this.map.put(person.getId(), person);
    }
  }

  public CachedPeopleDao() {
    this.map = new HashMap<>();
  }

  public Map<UUID, Person> getStudents() {
    Map<UUID, Person> students = new HashMap<>();
    for (Person person: map.values()) {
      if (person instanceof Student) {
        students.put(person.getId(), person);
      }
    }
    return students;
  }

  public Map<UUID, Person> getTeachers() {
    Map<UUID, Person> teachers = new HashMap<>();
    for (Person person: map.values()) {
      if (person instanceof Teacher) {
        teachers.put(person.getId(), person);
      }
    }
    return teachers;
  }
}
