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
   */
  private static final Pattern doublePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
  private static final Pattern twoDoublesPattern = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+ [-+]?[0-9]*\\.?[0-9]+.*$");

  public void process(String cmd) {
    /*
     *   determines automatically which command to execute.
     */
    Matcher doubleMatcher = doublePattern.matcher(cmd);
    Matcher twoDoublesMatcher = twoDoublesPattern.matcher(cmd);
    if (doubleMatcher.matches()) {
      sqrAndCbrtNumber(cmd);
    } else if (twoDoublesMatcher.matches()) {
      processTwoNums(cmd);
    } else {
      countSortString(cmd);
    }
  }

  private void sqrAndCbrtNumber(String input) {
    /*
     *    prints sqr(x) and cbrt(sqr(x))
     */
    final double num = Double.parseDouble(input);
    double square = Math.pow(num, 2);
    System.out.printf("Square: %.3f\n", square);
    System.out.printf("Cubic root of square: %.3f\n", Math.cbrt(square));
  }

  private void processTwoNums(String input) {
    /*
     *    parses two nums a,b and prints a/b
     */
    Matcher matcher = doublePattern.matcher(input);
    if (!matcher.find()) return;
    double num1 = Double.parseDouble(matcher.group());
    if (!matcher.find()) return;
    double num2 = Double.parseDouble(matcher.group());
    if (num2 == 0.0) {
      System.out.println("Can't divide by zero");
      return;
    }
    double result = num1 / num2;
    System.out.printf("Division: %.3f\n", result);
  }

  private void countSortString(String input) {
    char[] charArray = input.toCharArray();
    Map<Character, Integer> inputHashMap = new HashMap<>();
    StringBuilder sortedString = new StringBuilder();
    for (char key : charArray) {
      if (inputHashMap.containsKey(key)) {
        int count = inputHashMap.get(key);
        inputHashMap.put(key, count + 1);
      } else {
        inputHashMap.put(key, 1);
      }
    }
    Map<Character, Integer> sortedMap = new TreeMap<>(inputHashMap);
    for (Entry<Character, Integer> entry : sortedMap.entrySet()) {
      sortedString.append(String.valueOf(entry.getKey()).repeat(entry.getValue()));
      System.out.printf("'%c': %d\n", entry.getKey(), entry.getValue());
    }
    System.out.println(sortedString);
    System.out.println("Unique keys count: " + sortedMap.keySet().size());
  }

}
