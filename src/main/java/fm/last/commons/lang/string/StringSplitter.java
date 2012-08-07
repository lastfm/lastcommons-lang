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

/**
 * Helper class to parse a character sequence with varying delimiters and string token sizes.
 * 
 * @author martind
 */
public class StringSplitter {

  private final int oldLength;

  private String input;

  public StringSplitter(String input) {
    this.input = input;
    oldLength = input.length();
  }

  /**
   * Starting from the current reading position, extract the next token terminated by delimiter. Advances the read
   * pointer past the delimiter.
   * 
   * @param delimiter a regular expression that describes the next delimiter
   * @throws IllegalStateException if the current string value doesn't contain the delimiter sequence
   */
  public String getNextToken(String delimiter) {
    String[] tokens = input.split(delimiter, 2);
    if (tokens.length != 2) {
      throw new IllegalStateException("Token delimiter '" + delimiter + "' not found in string: '" + input + "'");
    }
    input = tokens[1];
    return tokens[0];
  }

  /**
   * Starting from the current reading position, extract the next numCharacters characters. Advances the read pointer
   * past the returned output.
   */
  public String getNextToken(int numCharacters) {
    String token = input.substring(0, numCharacters);
    input = input.substring(numCharacters);
    return token;
  }

  /**
   * Starting from the current reading position, extract the next numBytes bytes. Advances the read pointer past the
   * returned output. Caution: this will destroy successive characters when the read pointer is stopped at a
   * non-character boundary.
   */
  public byte[] getNextBytes(int numBytes) {
    byte[] buf = input.getBytes();
    input = new String(buf, numBytes, buf.length - numBytes);
    // return new String(buf, 0, numBytes);
    byte[] result = new byte[numBytes];
    for (int i = 0; i < numBytes; i++) {
      result[i] = buf[i];
    }
    return result;
  }

  public void skipDelimiter(String delimiter) {
    if (input.startsWith(delimiter)) {
      input = input.substring(delimiter.length());
    } else {
      throw new IllegalStateException("String does not start with token delimiter '" + delimiter + "': '" + input + "'");
    }
  }

  public String peek(int numChars) {
    return input.substring(0, numChars);
  }

  /**
   * Returns the current read pointer position (in number of characters).
   */
  public int getCurrentOffset() {
    return oldLength - input.length();
  }

}