package org.airng.labsecond;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Student extends Person {
  private final ArrayList<Subject> subjects;
  private final Map<Subject, Double> marks;

  Student(String fullName, Date birthDate, String phoneNumber, ArrayList<Subject> subjects, Map<Subject, Double> marks) {
    super(fullName, birthDate, phoneNumber);
    this.subjects = subjects;
    this.marks = marks;
  }

  public ArrayList<Subject> getSubjects() {
    return this.subjects;
  }

  public Map<Subject, Double> getMarks() {
    return this.marks;
  }

}
