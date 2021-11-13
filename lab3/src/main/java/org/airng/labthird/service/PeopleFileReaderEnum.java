package org.airng.labthird.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.airng.labthird.base.Person;
import org.airng.labthird.dao.CachedPeopleDao;
import org.airng.labthird.dao.Dao;

import java.io.File;
import java.io.IOException;

public enum PeopleFileReaderEnum {
  INSTANCE;

  public Dao<Person> ReadFromFile(String filename) throws IOException {
    try {
      return (new ObjectMapper()).readValue(new File(filename), CachedPeopleDao.class);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

}
