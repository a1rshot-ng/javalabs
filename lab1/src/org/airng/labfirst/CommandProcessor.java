package org.airng.labfirst;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.*;


public class CommandProcessor {
  /*
   *    Can process 3 different commands:
   *   for double, two doubles, and string.
   *
   *   process() automatically determines which command to execute.
   */
  private static final Pattern doublePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
  private static final Pattern twoDoublesPattern = Pattern.compile("^([0-9]+[a-zA-Z0-9]*\\.?[a-zA-Z0-9]* *){2}$");
  // allows non-numeric characters which are going to be filtered out.

  public static void process(String cmd) {
    Matcher doubleMatcher = doublePattern.matcher(cmd);
    Matcher twoDoublesMatcher = twoDoublesPattern.matcher(cmd);
    if (doubleMatcher.matches()) {
      CommandProcessor.processNumber(cmd);
    } else if (twoDoublesMatcher.matches()) {
      CommandProcessor.processTwoNums(cmd);
    } else {
      CommandProcessor.processString(cmd);
    }
  }

  private static void processNumber(String input) {
    double num = Double.parseDouble(input);
    num = Math.pow(num, 2);
    System.out.printf("Square: %.3f\n", num);
    num = Math.cbrt(num);
    System.out.printf("Cubic root of square: %.3f\n", num);
  }

  private static void processTwoNums(String input) {
    StringBuilder filtered = new StringBuilder();
    for (char letter: input.toCharArray()) {
      if ('0' <= letter && letter <= '9' || letter == '.' || letter == ' ' || letter == '\t')
        filtered.append(letter);
    }
    Matcher matcher = doublePattern.matcher(filtered);
    if (!matcher.find()) {
      processString(input);
      return;
    }
    double num1 = Double.parseDouble(matcher.group());
    if (!matcher.find()) {
      processString(input);
      return;
    }
    double num2 = Double.parseDouble(matcher.group());
    if (num2 == 0.0) {
      System.out.println("Can't divide by zero");
      return;
    }
    double result = num1 / num2;
    System.out.printf("Division: %.3f\n", result);
  }

  private static void processString(String input) {
    char[] charArray = input.toCharArray();
    Map<Character, Integer> inputHashMap = new HashMap<>();
    StringBuilder sortedString = new StringBuilder();
    for (char key: charArray) {
      if (inputHashMap.containsKey(key)) {
        int count = inputHashMap.get(key);
        inputHashMap.put(key, count+1);
      } else {
        inputHashMap.put(key, 1);
      }
    }
    TreeMap<Character, Integer> sortedMap = new TreeMap<>(inputHashMap);
    for (Entry<Character, Integer> entry: sortedMap.entrySet()) {
      sortedString.append(String.valueOf(entry.getKey()).repeat(entry.getValue()));
      System.out.printf("'%c': %d\n", entry.getKey(), entry.getValue());
    }
    System.out.println(sortedString);
  }

}
