package org.airng.labsecond.service;

import org.airng.labsecond.base.Person;
import org.airng.labsecond.base.Student;
import org.airng.labsecond.base.Subject;
import org.airng.labsecond.base.Teacher;
import org.airng.labsecond.dao.CachedPeopleDao;
import org.airng.labsecond.dao.Dao;
import org.airng.labsecond.dao.PeopleDao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PeopleService {
  private final Dao<Person> peopleDao;

  public PeopleService(String daoFolder) throws NullPointerException {
    this.peopleDao = new PeopleDao(daoFolder);
  }

  public PeopleService() {
    this.peopleDao = new CachedPeopleDao();
  }

  public int createTeacher(String fullName, Date birthDate, String phoneNumber, Subject subject, String workTime) {
    Teacher teacher = new Teacher(fullName, birthDate, phoneNumber, subject, workTime);
    try {
      peopleDao.save(teacher);
      return teacher.getId();
    } catch (IOException e) {
      System.err.println("Could not create teacher record #" + teacher.getId());
      return 0;
    }
  }

  public int createStudent(String fullName, Date birthDate, String phoneNumber, List<Subject> subjects, Map<Subject, Double> marks) {
    Student student = new Student(fullName, birthDate, phoneNumber, subjects, marks);
    try {
      peopleDao.save(student);
      return student.getId();
    } catch (IOException e) {
      System.err.println("Could not create student record #" + student.getId());
      return 0;
    }
  }

  public Person getPerson(int id) {
    Optional<Person> person = peopleDao.get(id);
    return person.orElse(null);
  }

  public void deletePerson(Person person) {
    this.deletePerson(person.getId());
  }

  public void deletePerson(int id) {
    try {
      peopleDao.delete(id);
    } catch (IOException e) {
      System.err.println("Could not delete person record #" + id);
    }
  }

  public int updatePerson(Command cmd) {
    Person person = this.getPerson(cmd.getId());
    if (person == null) {
      System.err.println("Could not find person record #" + cmd.getId());
      return 0;
    }
    if (person instanceof Teacher) {
      if (cmd.getTeacherSubject() != null) ((Teacher) person).subject = cmd.getTeacherSubject();
      if (cmd.getTeacherWorkTime() != null) ((Teacher) person).workTime = cmd.getTeacherWorkTime();
    } else if (person instanceof Student) {
      if (cmd.getStudentSubjects() != null) ((Student) person).subjects = cmd.getStudentSubjects();
      if (cmd.getStudentMarks() != null) {
        Double mark;
        for (Subject subject: ((Student) person).subjects)
          if ((mark = cmd.getStudentMarks().get(subject)) != null)
            ((Student) person).marks.put(subject, mark);
      }
    } else throw new RuntimeException();
    int id = person.getId();
    try {
      peopleDao.save(person);
      return id;
    } catch (IOException e) {
      System.err.printf("Could not update person record #%d\n", cmd.getId());
      return 0;
    }
  }

  public void listAllPeople() {
    List<Person> people = peopleDao.getAll();
    for (Person person: people)
      person.printInfo();
  }
}
