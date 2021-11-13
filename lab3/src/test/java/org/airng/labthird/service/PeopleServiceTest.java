package org.airng.labthird.service;

import org.airng.labthird.base.Person;
import org.airng.labthird.base.Student;
import org.airng.labthird.base.Subject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PeopleServiceTest {
  Map<UUID, Person> myPeople;
  PeopleService peopleService;

  public PeopleServiceTest() {
    myPeople = new HashMap<>();
    peopleService = new PeopleService();
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    List<Subject> subjects = new ArrayList<>();
    subjects.add(Subject.DISCMATH);
    Map<Subject, Double> marks = new HashMap<>();
    marks.put(Subject.DISCMATH, 5.0);
    try {
      Student student = new Student("bbb", date.parse("09.12.1998"), "5678", subjects, marks);
      UUID id = peopleService.createStudent("bbb", date.parse("09.12.1996"), "5678", subjects, marks);
      myPeople.put(id, student);
    } catch (ParseException ignored) { }
  }

  @Test
  public void testCreateStudent() {
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    List<Subject> subjects = new ArrayList<>();
    subjects.add(Subject.LINALG);
    Map<Subject, Double> marks = new HashMap<>();
    marks.put(Subject.LINALG, 4.6);
    try {
      Student student = new Student("aaa", date.parse("11.11.2011"), "1233", subjects, marks);
      myPeople.put(student.getId(), student);
      UUID id = peopleService.createStudent("aaa", date.parse("11.11.2011"), "1233", subjects, marks);
      Assert.assertNotNull(peopleService.getPerson(id));
    } catch (ParseException ignored) { }
  }

  @Test
  public void testUpdatePerson() {
    Assert.assertNull(peopleService.updatePerson(new UpdateCmd(UUID.randomUUID(), null, null, null, null)));
    List<UUID> uuids = myPeople.keySet().stream().toList();
    Assert.assertNotEquals(0, uuids.size());
    UUID uuid = uuids.get(0);
    List<Subject> subjects = new ArrayList<>();
    subjects.add(Subject.ALGORITHMS);
    Map<Subject, Double> marks = new HashMap<>();
    marks.put(Subject.ALGORITHMS, 4.8);
    UpdateCmd cmd = new UpdateCmd(uuid, subjects, marks, null, null);
    UUID updated = peopleService.updatePerson(cmd);
    Assert.assertNotNull(updated);
    Assert.assertNotNull(peopleService.getPerson(updated));
    Assert.assertEquals(marks, ((Student) peopleService.getPerson(updated)).marks);
  }

  @Test
  public void testDeletePerson() {
    try {
      peopleService.deletePerson(UUID.randomUUID());
      Assert.fail();
    } catch (IOException ignored) { }
    List<UUID> uuids = myPeople.keySet().stream().toList();
    Assert.assertNotEquals(0, uuids.size());
    UUID uuid = uuids.get(0);
    myPeople.remove(uuid);
    try {
      Assert.assertNotNull(peopleService.getPerson(uuid));
      peopleService.deletePerson(uuid);
      Assert.assertNull(peopleService.getPerson(uuid));
      myPeople.remove(uuid);
    } catch (IOException e) {
      Assert.fail();
    }
  }

}
