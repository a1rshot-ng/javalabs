package org.airng.labsecond.base;

import org.airng.labsecond.service.Action;
import org.airng.labsecond.service.Command;
import org.airng.labsecond.service.PeopleService;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.*;

public class CommandLineExec {
  private final PeopleService peopleService;
  private final String cmdFolder;
  private final Scanner sc = new Scanner(System.in);

  public CommandLineExec(PeopleService peopleService, String cmdFolder) {
    this.peopleService = peopleService;
    this.cmdFolder = cmdFolder;
  }

  public void interactive() {
    String command;
    while (true) {
      try {
        command = sc.nextLine();
        switch (command.toLowerCase()) {
          case "" -> { }
          case "add" -> this.addPerson();
          case "get" -> this.getPerson();
          case "list" -> this.listPeople();
          case "update" -> this.updatePerson();
          case "delete" -> this.deletePerson();
          case "--h" -> this.printHelp();
          case "q" -> {
            System.out.println("Thank you for choosing out product! Good bye.");
            return;
          }
          default -> System.err.println("Unknown command. Try again or type --h");
        }
      } catch (InputMismatchException e) {
        System.err.println("Invalid input format. Try again");
      } catch (NoSuchElementException e) {
        System.out.println("Good bye.");
        return;
      }
    }
  }

  private void printHelp() {
    System.out.println("Available commands:\n" +
        "  add  get  list  update  delete  --h  q");
  }

  private void listPeople() {
    this.peopleService.listAllPeople();
  }

  private void addPerson() {
    Command cmd = new Command(Action.ADD);

    System.out.print("Full Name:  ");
    cmd.fullName = sc.nextLine();

    System.out.print("Date of birth:  ");
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    try {
      cmd.birthDate = date.parse(sc.nextLine());
    } catch (ParseException e) {
      System.err.println("Invalid date format");
      return;
    }

    System.out.print("Phone:  ");
    cmd.phoneNumber = sc.nextLine();

    System.out.print("Role (t/s): ");
    String role = sc.nextLine();

    if ("s".equals(role)) {
      cmd.personType = PersonType.STUDENT;
      cmd.studentSubjects = new ArrayList<>();
      cmd.studentMarks = new HashMap<>();
      System.out.println("Tick student's subjects with any char:");
      for (Subject subject: Subject.values()) {
        System.out.print(subject.toString() + " | ");
        if (! "".equals(sc.nextLine()) ) cmd.studentSubjects.add(subject);
      }
      System.out.println("Enter student's marks:");
      for (Subject subject: cmd.studentSubjects) {
        System.out.print(subject.toString() + ":  ");
        cmd.studentMarks.put(subject, sc.nextDouble());
      }
    } else if ("t".equals(role)) {
      cmd.personType = PersonType.TEACHER;
      System.out.println("Select teacher's subject:");
      for (int i = 1; i < Subject.values().length + 1; i++)
        System.out.println(i + ". " + Subject.values()[i-1].toString());
      cmd.teacherSubject = Subject.values()[sc.nextInt() % Subject.values().length];
      System.out.print("Worktime:  ");
      sc.nextLine();
      cmd.teacherWorkTime = sc.nextLine();
    } else {
      System.err.println("Invalid role choice");
      return;
    }

    this.postCmd(cmd);
  }

  private void getPerson() {
    Command cmd = new Command(Action.GET);
    System.out.print("Enter id: ");
    cmd.id = sc.nextInt();
    this.postCmd(cmd);
  }

  private void deletePerson() {
    Command cmd = new Command(Action.DELETE);
    System.out.print("Enter id: ");
    cmd.id = sc.nextInt();
    this.postCmd(cmd);
  }

  private void updatePerson() {
    Command cmd = new Command(Action.UPDATE);
    System.out.print("Enter id: ");
    cmd.id = sc.nextInt();
    Person person = peopleService.getPerson(cmd.id);

    if (person != null) cmd.personType = person instanceof Teacher ? PersonType.TEACHER : PersonType.STUDENT;
    else {
      System.err.println("Person record #" + cmd.id + " not found");
      return;
    }
    System.out.println("(leave all empty to keep current value)");

    if (cmd.personType == PersonType.TEACHER) {
      System.out.println("Select new subject: ");
      for (int i = 1; i < Subject.values().length + 1; i++)
        System.out.println(i + ". " + Subject.values()[i-1].toString());
      sc.nextLine();
      String input = sc.nextLine();
      if (!"".equals(input)) cmd.teacherSubject = Subject.values()[Integer.parseInt(input) % Subject.values().length];

      System.out.print("Worktime: ");
      input = sc.nextLine();
      if (!"".equals(input)) cmd.teacherWorkTime = input;

    } else if (cmd.personType == PersonType.STUDENT) {
      cmd.studentSubjects = new ArrayList<>();
      cmd.studentMarks = new HashMap<>();
      System.out.println("Tick new subjects:");
      sc.nextLine();
      for (Subject subject: Subject.values()) {
        System.out.print(subject.toString() + " | ");
        if (! "".equals(sc.nextLine()) ) cmd.studentSubjects.add(subject);
      }
      assert person instanceof Student;
      if (cmd.studentSubjects.size() == 0) {
        cmd.studentSubjects = null;
      }
      System.out.println("(leave field empty if not present)\nEnter student's marks: ");
      for (Subject subject : (cmd.studentSubjects != null ? cmd.studentSubjects : Arrays.stream(Subject.values()).toList())) {
        System.out.print(subject.toString() + ":  ");
        try {
          String inp = sc.nextLine();
          if (!"".equals(inp))
            cmd.studentMarks.put(subject, Double.parseDouble(inp));
        } catch (InputMismatchException ignored) { }
      }
      if (cmd.studentMarks.size() == 0)
        cmd.studentMarks = null;
    }

    postCmd(cmd);
  }

  private void postCmd(Command cmd) {
    int commandId = cmd.hashCode();
    File output = new File(cmdFolder + "/" + commandId);
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(output, cmd);
    } catch (IOException e) {
      System.err.println("Could not post command to file " + commandId);
    }
  }
}
