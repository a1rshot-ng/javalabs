package org.airng.labsecond.service;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CommandChecker extends Thread {
  private final PeopleController peopleController;
  private final File cmdFolder;
  public volatile boolean stop = false;

  public CommandChecker(PeopleController peopleController, File cmdFolder) {
    this.peopleController = peopleController;
    this.cmdFolder = cmdFolder;
  }

  public void run() {
    long delayMillis = 1000;
    ObjectMapper objectMapper = new ObjectMapper();
    while (!stop) {
      try {
        sleep(delayMillis);

        File[] cmdFiles = cmdFolder.listFiles();
        if (cmdFiles != null && cmdFiles.length != 0) {
          for (File fileEntry: cmdFiles) {
            Command cmd = objectMapper.readValue(fileEntry, Command.class);
            peopleController.addCmd(cmd);
            if (!fileEntry.delete()) throw new IOException();
          }
        }

      } catch (IOException|NullPointerException e) {
        System.err.println("Could not process command in recent file");
      } catch (InterruptedException e) {
        return;
      }
    }
  }
}
