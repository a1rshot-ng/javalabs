package org.airng.labsecond.base;

import org.airng.labsecond.service.PeopleController;
import org.airng.labsecond.service.PeopleService;

public class Main {
  private static final String peopleFolder = "data/people";
  private static final String cmdFolder = "data/command";

  public static void main(String[] args) {
    PeopleService peopleService;
    PeopleController peopleController;
    try {
      peopleService = new PeopleService(peopleFolder);
      peopleController = new PeopleController(cmdFolder, peopleService);
    } catch (NullPointerException e) {
      System.err.println("Could not initialize services as " + peopleFolder + " or " + cmdFolder + " is unreachable");
      return;
    }
    CommandLineExec commandLineExec = new CommandLineExec(peopleService, cmdFolder);

    commandLineExec.interactive();
    peopleController.stopServices();
  }
}
