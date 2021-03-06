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

import java.util.Date;

import org.junit.Test;

public class SafeStringBuilderTest {

  @Test
  public void basicString() {
    SafeStringBuilder builder = new SafeStringBuilder(";", "-", "0", "D");
    builder.append("Test1-1");
    String test = null;
    builder.append(test);
    builder.append("Test1-2");
    assertEquals("Test1-1;-;Test1-2", builder.toString());
  }

  @Test
  public void basicAllDefault() {
    SafeStringBuilder builder = new SafeStringBuilder(";", "-", "0", "D");
    String test = null;
    builder.append(test);
    Integer testInt = null;
    builder.append(testInt);
    Date testDate = null;
    builder.append(testDate);
    Long testLong = null;
    builder.append(testLong);
    assertEquals("-;0;D;0", builder.toString());
  }

  @Test
  public void basicMixed() {
    SafeStringBuilder builder = new SafeStringBuilder(";", "-", "0", "D");
    String test = "Test3";
    builder.append(test);
    Integer testInt = Integer.valueOf(9);
    builder.append(testInt);
    Date testDate = new Date(1328095974000l);
    builder.append(testDate);
    Long testLong = Long.valueOf(689);
    builder.append(testLong);
    builder.append(new Float(0.9));
    String testNull = null;
    builder.append(testNull);
    assertEquals("Test3;9;" + testDate + ";689;0.9;-", builder.toString());
  }

  @Test
  public void advancedMixed() {
    SafeStringBuilder builder = new SafeStringBuilder(";", "-", "0", "D");
    Long test = null;
    builder.appendAsString(test);
    Integer testInt = Integer.valueOf(9);
    builder.append(testInt);
    Date testDate = null;
    builder.appendAsNumber(testDate);
    Long testLong = Long.valueOf(689);
    builder.append(testLong);
    builder.append(new Float(0.9));
    String testNull = null;
    builder.appendAsDate(testNull);
    assertEquals("-;9;0;689;0.9;D", builder.toString());
  }
}
