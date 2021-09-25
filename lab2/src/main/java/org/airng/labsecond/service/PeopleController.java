package org.airng.labsecond.service;

import java.io.File;
import java.util.*;

public class PeopleController {
  private final Queue<Command> cmdQueue = new ArrayDeque<>();
  private final CommandChecker commandChecker;
  private final CommandProcessor commandProcessor;

  public PeopleController(String cmdPath, PeopleService peopleService) throws NullPointerException {
    File cmdFolder = new File(cmdPath);
    Objects.requireNonNull(cmdFolder);
    commandChecker = new CommandChecker(this, cmdFolder);
    commandProcessor = new CommandProcessor(this, peopleService);
    commandChecker.start();
    commandProcessor.start();
  }

  public Command getCmd() {
    return this.cmdQueue.poll();
  }

  public void addCmd(Command cmd) {
    this.cmdQueue.add(cmd);
  }

  public void stopServices() {
    commandChecker.stop = true;
    commandProcessor.stop = true;
  }
}
