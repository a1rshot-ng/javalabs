package org.airng.labsecond.dao;

import org.airng.labsecond.base.Person;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.airng.labsecond.base.Student;
import org.airng.labsecond.base.Teacher;
import org.codehaus.jackson.map.ObjectMapper;

public class PeopleDao implements Dao<Person> {
  private final File folder;

  public PeopleDao(String folder) throws NullPointerException {
    this.folder = new File(folder);
    Objects.requireNonNull(this.folder.listFiles());
  }

  @Override
  public Optional<Person> get(int id) {
    try {
      File file = new File(folder.getPath() + "/" + id);
      return Optional.ofNullable(this.readFromFile(file));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Person> getAll() {
    List<Person> people = new ArrayList<>();
    for (File fileEntry: Objects.requireNonNull(this.folder.listFiles())) {
      try {
        Person person = this.readFromFile(fileEntry);
        people.add(person);
      } catch (IOException e) {
        System.err.printf("Could not read file \"%s\" from the record folder\n", fileEntry.getName());
      }
    }
    return people;
  }

  @Override
  public void save(Person person) throws IOException {
    int id = person.getId();
    File f = new File(folder.getPath() + "/" + id);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(f, person);
  }

  @Override
  public void delete(int id) throws IOException {
    File f = new File(folder.getPath() + "/" + id);
    if (!f.delete()) throw new IOException();
  }

  private Person readFromFile(File fileEntry) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Person person;
    try {
      person = objectMapper.readValue(fileEntry, Teacher.class);
    } catch (IOException e) {
      person = objectMapper.readValue(fileEntry, Student.class);
    }
    return person;
  }
}
