package org.airng.labsecond.dao;

import org.airng.labsecond.base.Person;
import org.airng.labsecond.base.Student;
import org.airng.labsecond.base.Subject;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class CachedPeopleDaoTest {
  CachedPeopleDao cachedPeopleDao;
  Map<Integer, Person> myPeople;

  public CachedPeopleDaoTest() {
    cachedPeopleDao = new CachedPeopleDao();
    myPeople = new HashMap<>();
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    List<Subject> subjects = new ArrayList<>();
    subjects.add(Subject.DISCMATH);
    Map<Subject, Double> marks = new HashMap<>();
    marks.put(Subject.DISCMATH, 5.0);
    try {
      Student student = new Student("bbb", date.parse("09.12.1998"), "5678", subjects, marks);
      cachedPeopleDao.save(student);
      myPeople.put(student.getId(), student);
    } catch (ParseException ignored) { }
  }

  private boolean compareLists(List<Person> p1, List<Person> p2) {
    if (p1 == null || p2 == null) return false;
    if (p1.size() != p2.size()) return false;
    for (int i = 0; i < p1.size(); i++)
      if (p1.get(i).getId() != p2.get(i).getId()) return false;
    return true;
  }

  @Test
  public void testGetAll() {
    List<Person> people = cachedPeopleDao.getAll();
    assertTrue(compareLists(myPeople.values().stream().toList(), people));
  }

  @Test
  public void testSave() {
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    List<Subject> subjects = new ArrayList<>();
    subjects.add(Subject.ALGORITHMS);
    Map<Subject, Double> marks = new HashMap<>();
    marks.put(Subject.ALGORITHMS, 5.0);
    try {
      Student student = new Student("aaa", date.parse("07.05.1997"), "1234", subjects, marks);
      cachedPeopleDao.save(student);
      myPeople.put(student.getId(), student);
    } catch (ParseException ignored) { }
    assertTrue(compareLists(myPeople.values().stream().toList(), cachedPeopleDao.getAll()));
  }

  @Test
  public void testDelete() {
    if (cachedPeopleDao.getAll().isEmpty() && myPeople.isEmpty()) return;
    int id = cachedPeopleDao.getAll().get(0).getId();
    try {
      cachedPeopleDao.delete(id);
      myPeople.remove(id);
    } catch (IOException ignored) { }
    assertTrue(compareLists(myPeople.values().stream().toList(), cachedPeopleDao.getAll()));
  }
}
