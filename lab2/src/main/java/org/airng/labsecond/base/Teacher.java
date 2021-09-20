package org.airng.labsecond;

import java.util.Date;

public class Teacher extends Person {
  private final String subject, workTime;

  Teacher(String fullName, Date birthDate, String phoneNumber, String subject, String workTime) {
    super(fullName, birthDate, phoneNumber);
    this.subject = subject;
    this.workTime = workTime;
  }

  public String getSubject() {
    return this.subject;
  }

  public String getWorkTime() {
    return this.workTime;
  }

}
