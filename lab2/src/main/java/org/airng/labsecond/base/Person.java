package org.airng.labsecond.base;

import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class Person {
  public int id = -1;

  public String fullName;
  public Date birthDate;
  public String phoneNumber;

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
    System.out.printf("-----------------------------------\nPerson record #%d\n", this.getId());
    System.out.println("Name:   " + this.getFullName());
    System.out.println("DoB:    " + dateFormat.format(this.getBirthDate()));
    System.out.println("Phone:  " + this.getPhoneNumber());
  }

  private void initId() {
    int hash = 1;
    hash += fullName.hashCode(); hash &= 0xffffffff;
    hash += birthDate.hashCode(); hash &= 0xffffffff;
    hash += phoneNumber.hashCode(); hash &= 0xffffffff;
    this.id = hash;
  }

  public int getId() {
    if (this.id == -1)
      this.initId();
    return this.id;
  }

}
