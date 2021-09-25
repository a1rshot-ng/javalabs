package org.airng.labsecond.service;

import org.airng.labsecond.base.Person;
import org.airng.labsecond.base.PersonType;

public class CommandProcessor extends Thread {
  private final PeopleController peopleController;
  private final PeopleService peopleService;
  public volatile boolean stop = false;

  public CommandProcessor(PeopleController peopleController, PeopleService peopleService) {
    this.peopleController = peopleController;
    this.peopleService = peopleService;
  }

  public void run() {
    long delayMillis = 1000;
    Command cmd;

    while (!stop) {
      try {
        sleep(delayMillis);
        cmd = peopleController.getCmd();
        if (cmd != null) {
          switch (cmd.action) {
            case ADD:
              if (cmd.personType == PersonType.TEACHER) {
                int id = peopleService.createTeacher(cmd.fullName, cmd.birthDate, cmd.phoneNumber, cmd.teacherSubject, cmd.teacherWorkTime);
                if (id != 0) System.out.println("Teacher created with id #" + id);
              }
              else {// personType == PersonType.STUDENT)
                int id = peopleService.createStudent(cmd.fullName, cmd.birthDate, cmd.phoneNumber, cmd.studentSubjects, cmd.studentMarks);
                if (id != 0) System.out.println("Student created with id #" + id);
              }
              break;
            case GET:
              Person person = peopleService.getPerson(cmd.id);
              if (person != null) person.printInfo();
              break;
            case DELETE:
              peopleService.deletePerson(cmd.id);
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
