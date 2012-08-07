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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringSplitterTest {

  @Test
  public void getToke() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wrdchomp = new StringSplitter(wrddz);
    assertEquals("SAY", wrdchomp.getNextToken(" "));
  }

  @Test(expected = IllegalStateException.class)
  public void getTokeBadDeli() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    assertEquals("SAY", wordchomp.getNextToken(":"));
  }

  @Test
  public void getTokeByInt() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    assertEquals("SAY A WORD", wordchomp.getNextToken(10));
  }

  @Test
  public void getTokeByBytes() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    String partwrd = "SAY A WORD";
    byte[] mawordz = partwrd.getBytes();
    assertArrayEquals(mawordz, wordchomp.getNextBytes(10));
  }

  @Test
  public void getPeek() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    wordchomp.getNextToken(" ");
    wordchomp.peek(5);
    assertEquals("A", wordchomp.getNextToken(" "));
  }

  @Test
  public void getOffset() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    wordchomp.getNextToken(" ");
    wordchomp.getNextToken(" ");
    assertEquals(6, wordchomp.getCurrentOffset());
  }

  @Test
  public void skipDaDeli() {
    String wrddz = " SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    wordchomp.skipDelimiter(" ");
    assertEquals("SAY", wordchomp.getNextToken(" "));
  }

  @Test(expected = IllegalStateException.class)
  public void skipDaDeliException() {
    String wrddz = "SAY A WORD FOR JIMMY BROWN";
    StringSplitter wordchomp = new StringSplitter(wrddz);
    wordchomp.skipDelimiter(" ");
  }

}
