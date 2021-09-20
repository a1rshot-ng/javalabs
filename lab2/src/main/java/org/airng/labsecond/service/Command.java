package org.airng.labsecond.service;

import org.airng.labsecond.base.Subject;

import java.util.List;
import java.util.Map;

public class PeopleCommand {
  public Command action;  // get, add, update, delete
  public int id;  // get, update, delete

  public String teacherSubject;  // update (teacher)
  public String teacherWorkTime;

  public List<Subject> studentSubjects;  // update (student)
  public Map<Subject, Double> studentMarks;
}
