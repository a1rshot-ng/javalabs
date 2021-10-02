package org.airng.labsecond.service;

import org.airng.labsecond.base.Person;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CommandController extends Thread {
  private final PeopleControlLauncher peopleControlLauncher;
  private final File cmdFolder;
  public volatile boolean stop = false;

  public CommandController(PeopleControlLauncher peopleControlLauncher, File cmdFolder) {
    this.peopleControlLauncher = peopleControlLauncher;
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
            peopleControlLauncher.addCmd(cmd);
            if (!fileEntry.delete()) throw new IOException();
          }
        }

      } catch (IOException|NullPointerException e) {
        System.err.println("Controller: could not process command in recent file: " + e);
        e.printStackTrace(System.err);
      } catch (InterruptedException e) {
        return;
      }
    }
  }
}
