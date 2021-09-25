package org.airng.labsecond.dao;

import org.airng.labsecond.base.Person;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CachedPeopleDao implements Dao<Person> {
  private final Map<Integer, Person> people = new HashMap<>();

  @Override
  public Optional<Person> get(int id) {
    return Optional.ofNullable(people.get(id));
  }

  @Override
  public List<Person> getAll() {
    return people.values().stream().toList();
  }

  @Override
  public void save(Person person) {
    people.put(person.getId(), person);
  }

  @Override
  public void delete(int id) throws IOException {
    if (people.remove(id) == null) throw new IOException();
  }

}
