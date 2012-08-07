/*
 * Copyright 2012 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.commons.lang.string;

import java.util.Arrays;

/**
 * Utility class for doing string manipulation.
 */
public class LastStringUtils {

  private LastStringUtils() {
  }

  /**
   * Escapes the unicode in the given string.
   * 
   * @param input The input string.
   * @param escapeAscii Whether to escape ASCII or not.
   * @return The escaped string.
   */
  public static String escapeUnicodeString(final String input, final boolean escapeAscii) {
    StringBuilder returnValue = new StringBuilder("");
    for (int i = 0; i < input.length(); i++) {
      char ch = input.charAt(i);
      if (!escapeAscii && ch >= 0x0020 && ch <= 0x007e) {
        returnValue.append(ch);
      } else {
        returnValue.append("\\u");

        String hex = Integer.toHexString(input.charAt(i) & 0xFFFF);
        // If the hex string is less than 4 characters long, fill it up with leading zeroes
        char[] zeroes = new char[4 - hex.length()];
        Arrays.fill(zeroes, '0');

        returnValue.append(zeroes);
        returnValue.append(hex.toUpperCase());
      }
    }
    return returnValue.toString();
  }

  /**
   * Truncates the passed String to the passed maximum length, if it exceeds the maximum length.
   * 
   * @param input String to truncate.
   * @param maxLength Maximum length for String.
   * @return Input string truncated to the max length.
   */
  public static String truncate(String input, int maxLength) {
    if (input != null && input.length() > maxLength) {
      return input.substring(0, maxLength);
    }
    return input;
  }

  /**
   * Strips "non-printable" whitespace (carriage returns, tabs, linefeeds etc.) from the passed string.
   * 
   * @param input Input String.
   * @return Input with whitespace removed.
   */
  public static String removeNonPrintableWhitespace(String input) {
    if (input != null) {
      input = input.replaceAll("[\\f\\t\\r\\n]+", "");
    }
    return input;
  }

}
