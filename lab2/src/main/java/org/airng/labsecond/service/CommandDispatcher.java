package org.airng.labsecond.service;

import org.airng.labsecond.base.Person;
import org.airng.labsecond.base.PersonType;

public class CommandDispatcher extends Thread {
  private final PeopleControlLauncher peopleControlLauncher;
  private final PeopleService peopleService;
  public volatile boolean stop = false;

  public CommandDispatcher(PeopleControlLauncher peopleControlLauncher, PeopleService peopleService) {
    this.peopleControlLauncher = peopleControlLauncher;
    this.peopleService = peopleService;
  }

  public void run() {
    long delayMillis = 1000;
    Command cmd;

    while (!stop) {
      try {
        sleep(delayMillis);
        cmd = peopleControlLauncher.getCmd();
        if (cmd != null) {
          switch (cmd.getAction()) {
            case ADD:
              if (cmd.getPersonType() == PersonType.TEACHER) {
                int id = peopleService.createTeacher(cmd.getFullName(), cmd.getBirthDate(), cmd.getPhoneNumber(), cmd.getTeacherSubject(), cmd.getTeacherWorkTime());
                if (id != 0) System.out.println("Teacher created with id #" + id);
              }
              else {// personType == PersonType.STUDENT)
                int id = peopleService.createStudent(cmd.getFullName(), cmd.getBirthDate(), cmd.getPhoneNumber(), cmd.getStudentSubjects(), cmd.getStudentMarks());
                if (id != 0) System.out.println("Student created with id #" + id);
              }
              break;
            case GET:
              Person person = peopleService.getPerson(cmd.getId());
              if (person != null) person.printInfo();
              break;
            case DELETE:
              peopleService.deletePerson(cmd.getId());
              break;
            case UPDATE:
              int id = peopleService.updatePerson(cmd);
              if (id != 0) System.out.println("Person updated with id #" + id);
          }
        }
      } catch (InterruptedException e) {
        return;
      }

    }
  }
}
