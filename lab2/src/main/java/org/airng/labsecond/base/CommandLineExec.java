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
    PersonType personType;
    String fullName, phoneNumber, teacherWorkTime = null;
    Date birthDate;
    Subject teacherSubject = null;
    List<Subject> studentSubjects = null;
    Map<Subject, Double> studentMarks = null;

    System.out.print("Full Name:  ");
    fullName = sc.nextLine();

    System.out.print("Date of birth:  ");
    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
    try {
      birthDate = date.parse(sc.nextLine());
    } catch (ParseException e) {
      System.err.println("Invalid date format");
      return;
    }

    System.out.print("Phone:  ");
    phoneNumber = sc.nextLine();

    System.out.print("Role (t/s): ");
    String role = sc.nextLine();

    if ("s".equals(role)) {
      personType = PersonType.STUDENT;
      studentSubjects = new ArrayList<>();
      studentMarks = new HashMap<>();
      System.out.println("Tick student's subjects with any char:");
      for (Subject subject: Subject.values()) {
        System.out.print(subject.toString() + " | ");
        if (! "".equals(sc.nextLine()) ) studentSubjects.add(subject);
      }
      System.out.println("Enter student's marks:");
      for (Subject subject: studentSubjects) {
        System.out.print(subject.toString() + ":  ");
        studentMarks.put(subject, sc.nextDouble());
      }
    } else if ("t".equals(role)) {
      personType = PersonType.TEACHER;
      System.out.println("Select teacher's subject:");
      for (int i = 1; i < Subject.values().length + 1; i++)
        System.out.println(i + ". " + Subject.values()[i-1].toString());
      teacherSubject = Subject.values()[(sc.nextInt()-1) % Subject.values().length];
      System.out.print("Worktime:  ");
      sc.nextLine();
      teacherWorkTime = sc.nextLine();
    } else {
      System.err.println("Invalid role choice");
      return;
    }
    Command cmd = new Command(Action.ADD, -1, personType, fullName, birthDate, phoneNumber, studentSubjects, studentMarks, teacherSubject, teacherWorkTime);
    this.postCmd(cmd);
  }

  private void getPerson() {
    System.out.print("Enter id: ");
    Command cmd = new Command(Action.GET, sc.nextInt(), null, null, null, null, null, null, null, null);
    this.postCmd(cmd);
  }

  private void deletePerson() {
    System.out.print("Enter id: ");
    Command cmd = new Command(Action.DELETE, sc.nextInt(), null, null, null, null, null, null, null, null);
    this.postCmd(cmd);
  }

  private void updatePerson() {
    System.out.print("Enter id: ");
    int id = sc.nextInt();
    PersonType personType;

    Subject teacherSubject = null;
    String teacherWorkTime = null;
    List<Subject> studentSubjects = null;
    Map<Subject, Double> studentMarks = null;

    Person person = peopleService.getPerson(id);

    if (person != null) {
      personType = person instanceof Teacher ? PersonType.TEACHER : PersonType.STUDENT;
    }
    else {
      System.err.println("Person record #" + id + " not found");
      return;
    }
    System.out.println("(leave all empty to keep current value)");

    if (personType == PersonType.TEACHER) {
      System.out.println("Select new subject: ");
      for (int i = 1; i < Subject.values().length + 1; i++)
        System.out.println(i + ". " + Subject.values()[i-1].toString());
      sc.nextLine();
      String input = sc.nextLine();
      if (!"".equals(input)) teacherSubject = Subject.values()[(Integer.parseInt(input)-1) % Subject.values().length];

      System.out.print("Worktime: ");
      input = sc.nextLine();
      if (!"".equals(input)) teacherWorkTime = input;

    } else {
      studentSubjects = new ArrayList<>();
      studentMarks = new HashMap<>();
      System.out.println("Tick new subjects:");
      sc.nextLine();
      for (Subject subject: Subject.values()) {
        System.out.print(subject.toString() + " | ");
        if (! "".equals(sc.nextLine()) ) studentSubjects.add(subject);
      }
      assert person instanceof Student;
      if (studentSubjects.size() == 0) {
        studentSubjects = null;
      }
      System.out.println("(leave field empty if not present)\nEnter student's marks: ");
      for (Subject subject: (studentSubjects != null ? studentSubjects : Arrays.stream(Subject.values()).toList())) {
        System.out.print(subject.toString() + ":  ");
        try {
          String inp = sc.nextLine();
          if (!"".equals(inp))
            studentMarks.put(subject, Double.parseDouble(inp));
        } catch (InputMismatchException ignored) { }
      }
      if (studentMarks.size() == 0)
        studentMarks = null;
    }

    Command cmd = new Command(Action.UPDATE, id, null, null, null, null, studentSubjects, studentMarks, teacherSubject, teacherWorkTime);
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
