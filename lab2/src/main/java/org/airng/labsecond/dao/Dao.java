package org.airng.labsecond.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
  Optional<T> get(int id);
  List<T> getAll();
  void save(T t) throws IOException;
  void delete(int id) throws IOException;
}
