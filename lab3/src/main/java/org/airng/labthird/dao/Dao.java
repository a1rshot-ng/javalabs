package org.airng.labthird.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao<T> {
  Optional<T> get(UUID id);
  List<T> listAll();
  void save(T t) throws IOException;
  void delete(UUID id) throws IOException;
}
