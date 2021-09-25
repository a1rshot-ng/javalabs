package org.airng.labsecond.service;

import org.airng.labsecond.base.PersonType;
import org.airng.labsecond.base.Subject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Command {
  public Action action;  // get, add, update, delete
  public Integer id;  // get, update, delete

  public PersonType personType; // add
  public String fullName;
  public Date birthDate;
  public String phoneNumber;

  public List<Subject> studentSubjects; // add, update
  public Map<Subject, Double> studentMarks;

  public Subject teacherSubject; // add, update
  public String teacherWorkTime;

  public Command(Action action) {
    this.action = action;
  }

  public Command() { }
}
