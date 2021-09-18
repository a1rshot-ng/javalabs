package org.airng.labfirst;

import java.util.Scanner;


public class Main {
  /*
   *    First lab. The task is available on drive.
   */
  public static void main(String[] args) {
    final int MAX_LENGTH = 17;
    Scanner sc = new Scanner(System.in);
    String command;

    try {
      do {
        System.out.print(">>> ");
        command = sc.nextLine();

        if (command.length() > MAX_LENGTH) {
          System.out.println("Command is too long");
          continue;
        }

        switch (command) {
          case "--h" -> System.out.println("""
                        This is first Java lab.
                        Available commands:
                            [number]               calculates its square and cubic root of the square
                            [number1] [number2]    divides 1st by 2nd number
                            [string]               sorts the string and prints count of unique chars
                            --h                    prints this message
                            q                      quits the program""");
          case "q" -> System.out.println("Thank you for choosing our product! Good bye.");
          default -> CommandProcessor.process(command);
        }

      } while (!command.equals("q"));
    } catch (java.util.NoSuchElementException e) {
      // Ctrl+D pressed
      System.out.println("Good bye.");
    }
  }
}
