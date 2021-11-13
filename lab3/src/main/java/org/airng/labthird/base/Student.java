package org.airng.labthird.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Student extends Person {
  public List<Subject> subjects;
  public Map<Subject, Double> marks;

  public Student(String fullName, Date birthDate, String phoneNumber, List<Subject> subjects, Map<Subject, Double> marks) {
    super(fullName, birthDate, phoneNumber);
    this.subjects = subjects;
    this.marks = marks;
  }

  public Student() {
    super();
  }

  @Override
  public void printInfo() {
    super.printInfo();
    System.out.println("Role:   Student");
    System.out.println("Subjects:");
    for (Subject subject: this.subjects) {
      System.out.printf("    %s\n", subject.toString());
    }
    System.out.println("Marks:");
    for (Map.Entry<Subject, Double> entry: this.marks.entrySet()) {
      System.out.printf("    %s:  %.2f\n", entry.getKey().toString(), entry.getValue());
    }
  }
}
