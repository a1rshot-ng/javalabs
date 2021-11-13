package org.airng.labthird.service;

import org.airng.labthird.base.Subject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UpdateCmd {
  public final UUID id;

  public final List<Subject> studentSubjects;
  public final Map<Subject, Double> studentMarks;

  public final Subject teacherSubject;
  public final String teacherWorktime;

  public UpdateCmd (UUID id, List<Subject> studentSubjects, Map<Subject, Double> studentMarks, Subject teacherSubject, String teacherWorktime) {
    this.id = id;
    this.studentSubjects = studentSubjects;
    this.studentMarks = studentMarks;
    this.teacherSubject = teacherSubject;
    this.teacherWorktime = teacherWorktime;
  }

}
