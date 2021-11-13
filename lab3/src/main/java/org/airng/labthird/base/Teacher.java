package org.airng.labthird.base;

import java.util.Date;

public class Teacher extends Person {
  public Subject subject;
  public String workTime;

  public Teacher(String fullName, Date birthDate, String phoneNumber, Subject subject, String workTime) {
    super(fullName, birthDate, phoneNumber);
    this.subject = subject;
    this.workTime = workTime;
  }

  public Teacher() {
    super();
  }

  @Override
  public void printInfo() {
    super.printInfo();
    System.out.println("Role:   Teacher");
    System.out.println("Subject:  " + this.subject.toString());
    System.out.println("Worktime: " + this.workTime);
  }
}
