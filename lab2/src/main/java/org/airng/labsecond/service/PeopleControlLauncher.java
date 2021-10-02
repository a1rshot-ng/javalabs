package org.airng.labsecond.service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PeopleControlLauncher {
  private final Queue<Command> cmdQueue = new ConcurrentLinkedQueue<>();
  private final CommandController commandController;
  private final CommandDispatcher commandDispatcher;

  public PeopleControlLauncher(String cmdPath, PeopleService peopleService) throws NullPointerException {
    File cmdFolder = new File(cmdPath);
    Objects.requireNonNull(cmdFolder);
    commandController = new CommandController(this, cmdFolder);
    commandDispatcher = new CommandDispatcher(this, peopleService);
    commandController.start();
    commandDispatcher.start();
  }

  public Command getCmd() {
    return this.cmdQueue.poll();
  }

  public void addCmd(Command cmd) {
    this.cmdQueue.add(cmd);
  }

  public void stopServices() {
    commandController.stop = true;
    commandDispatcher.stop = true;
  }
}
