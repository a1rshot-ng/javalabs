package org.airng.labthird.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

abstract public class Person {
  private UUID id = null;

  private String fullName;
  private Date birthDate;
  private String phoneNumber;

  Person(String fullName, Date birthDate, String phoneNumber) {
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
    this.initId();
  }

  public Person() { }

  public String getFullName() {
    return this.fullName;
  }
  public Date getBirthDate() {
    return this.birthDate;
  }
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void printInfo() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
    System.out.printf("-----------------------------------\nPerson record #%s\n", this.getId().toString());
    System.out.println("Name:   " + this.getFullName());
    System.out.println("DoB:    " + dateFormat.format(this.getBirthDate()));
    System.out.println("Phone:  " + this.getPhoneNumber());
  }

  private void initId() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return this.id;
  }

}
