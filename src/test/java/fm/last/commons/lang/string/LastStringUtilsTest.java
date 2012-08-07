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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LastStringUtilsTest {

  @Test
  public void truncate() {
    String input = "abcdefghij";
    assertEquals("abc", LastStringUtils.truncate(input, 3));
    assertEquals(input, LastStringUtils.truncate(input, input.length()));
    assertEquals(input, LastStringUtils.truncate(input, input.length() + 10));
  }

  @Test
  public void truncateNull() {
    assertEquals(null, LastStringUtils.truncate(null, 3));
  }

  @Test
  public void removeNonPrintableWhitespace() {
    assertEquals(null, LastStringUtils.removeNonPrintableWhitespace(null));
    assertEquals("Herbie Hancock", LastStringUtils.removeNonPrintableWhitespace("Herbie Hancock"));
    assertEquals("Herbie Hancock", LastStringUtils.removeNonPrintableWhitespace("Herbie\t Hancock"));
    assertEquals("Herbie Hancock", LastStringUtils.removeNonPrintableWhitespace("Herbie\t Hancock\r\f"));
    assertEquals("Herbie Hancock", LastStringUtils.removeNonPrintableWhitespace("Herbie\t Hancock\r\n"));
    assertEquals("Herbie Hancock", LastStringUtils.removeNonPrintableWhitespace("Her\tbie\t\t Hancock\n\n"));
  }

  @Test
  public void escapeUnicodeString() {
    String input = "foo bar\r\n baz \u0123 \u0001\u1234 bat";
    String expected = "foo bar\\u000D\\u000A baz \\u0123 \\u0001\\u1234 bat";
    assertEquals(expected, LastStringUtils.escapeUnicodeString(input, false));
  }

  @Test
  public void escapeUnicodeStringWithAscii() {
    String input = "foo\n";
    String expected = "\\u0066\\u006F\\u006F\\u000A";
    assertEquals(expected, LastStringUtils.escapeUnicodeString(input, true));
  }

}
