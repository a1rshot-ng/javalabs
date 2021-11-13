package org.airng.labthird.service;

import org.airng.labthird.base.Person;
import org.airng.labthird.base.Student;
import org.airng.labthird.base.Subject;
import org.airng.labthird.base.Teacher;
import org.airng.labthird.dao.CachedPeopleDao;
import org.airng.labthird.dao.Dao;

import java.io.IOException;
import java.util.*;

public class PeopleService {
  private final Dao<Person> peopleDao;

  public PeopleService() {
    this.peopleDao = new CachedPeopleDao();
  }

  public PeopleService(Dao<Person> dao) {
    this.peopleDao = dao;
  }

  public UUID createTeacher(String fullName, Date birthDate, String phoneNumber, Subject subject, String workTime) {
    Teacher teacher = new Teacher(fullName, birthDate, phoneNumber, subject, workTime);
    try {
      peopleDao.save(teacher);
      return teacher.getId();
    } catch (IOException e) {
      System.err.println("Could not create teacher record #" + teacher.getId());
      return null;
    }
  }

  public UUID createStudent(String fullName, Date birthDate, String phoneNumber, List<Subject> subjects, Map<Subject, Double> marks) {
    Student student = new Student(fullName, birthDate, phoneNumber, subjects, marks);
    try {
      peopleDao.save(student);
      return student.getId();
    } catch (IOException e) {
      System.err.println("Could not create student record #" + student.getId());
      return null;
    }
  }

  public Person getPerson(UUID id) {
    Optional<Person> person = peopleDao.get(id);
    return person.orElse(null);
  }

  public void deletePerson(Person person) throws IOException {
    this.deletePerson(person.getId());
  }

  public void deletePerson(UUID id) throws IOException {
      peopleDao.delete(id);
  }

  public UUID updatePerson(UpdateCmd cmd) {
    Person person = this.getPerson(cmd.id);
    if (person == null) {
      System.err.println("Could not find person record #" + cmd.id);
      return null;
    }
    if (person instanceof Teacher) {
      if (cmd.teacherSubject != null) ((Teacher) person).subject = cmd.teacherSubject;
      if (cmd.teacherWorktime != null) ((Teacher) person).workTime = cmd.teacherWorktime;
    } else if (person instanceof Student) {
      if (cmd.studentSubjects != null) ((Student) person).subjects = cmd.studentSubjects;
      if (cmd.studentMarks != null) {
        Double mark;
        ((Student) person).marks = new HashMap<>();
        for (Subject subject: ((Student) person).subjects)
          if ((mark = cmd.studentMarks.get(subject)) != null)
            ((Student) person).marks.put(subject, mark);
      }
    } else throw new RuntimeException();
    try {
      peopleDao.save(person);
      return person.getId();
    } catch (IOException e) {
      System.err.printf("Could not update person record %s\n", cmd.id.toString());
      return null;
    }
  }

  public void listAllPeople() {
    List<Person> people = peopleDao.listAll();
    for (Person person: people)
      person.printInfo();
  }

  public Dao<Person> getDao() {
    return this.peopleDao;
  }
}
