package org.airng.labsecond;

import java.util.Date;

abstract class Person {
  private final String fullName;
  private final Date birthDate;
  private final String phoneNumber;

  Person(String fullName, Date birthDate, String phoneNumber) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
  }

  public String getFullName() {
    return this.fullName;
  }
  public Date getBirthDate() {
    return this.birthDate;
  }
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

}
