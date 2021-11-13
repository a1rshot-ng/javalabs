package org.airng.labsecond.service;

import org.airng.labsecond.base.PersonType;
import org.airng.labsecond.base.Subject;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Command {
  private final Action action;  // get, add, update, delete
  private final Integer id;  // get, update, delete

  private final PersonType personType; // add
  private final String fullName;
  private final Date birthDate;
  private final String phoneNumber;

  private final List<Subject> studentSubjects; // add, update
  private final Map<Subject, Double> studentMarks;

  private final Subject teacherSubject; // add, update
  private final String teacherWorkTime;

  @JsonCreator
  public Command(@JsonProperty("action") Action action, @JsonProperty("id") int id,
                 @JsonProperty("personType") PersonType personType, @JsonProperty("fullName") String fullName,
                 @JsonProperty("birthDate") Date birthDate, @JsonProperty("phoneNumber") String phoneNumber,
                 @JsonProperty("studentSubjects") List<Subject> studentSubjects, @JsonProperty("studentMarks") Map<Subject, Double> studentMarks,
                 @JsonProperty("teacherSubject") Subject teacherSubject, @JsonProperty("teacherWorkTime") String teacherWorkTime) {
    this.action = action;
    this.id = id;
    this.personType = personType;
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
    this.studentSubjects = studentSubjects == null ? null : Collections.unmodifiableList(studentSubjects);
    this.studentMarks = studentMarks == null ? null : Collections.unmodifiableMap(studentMarks);
    this.teacherSubject = teacherSubject;
    this.teacherWorkTime = teacherWorkTime;
  }

  public Action getAction() {
    return action;
  }

  public Integer getId() {
    return id;
  }

  public PersonType getPersonType() {
    return personType;
  }

  public String getFullName() {
    return fullName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public List<Subject> getStudentSubjects() {
    return studentSubjects;
  }

  public Map<Subject, Double> getStudentMarks() {
    return studentMarks;
  }

  public Subject getTeacherSubject() {
    return teacherSubject;
  }

  public String getTeacherWorkTime() {
    return teacherWorkTime;
  }
}
