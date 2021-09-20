package org.airng.labsecond.dao;

import org.airng.labsecond.base.Person;

import java.util.List;
import java.util.Optional;

public class PeopleCachedDao implements Dao<Person> {

  @Override
  public Optional<Person> get(Long id) {
    return Optional.empty();
  }

  @Override
  public List<Person> getAll() {
    return null;
  }

  @Override
  public void save(Person person) {

  }

  @Override
  public void delete(Person person) {

  }
}
