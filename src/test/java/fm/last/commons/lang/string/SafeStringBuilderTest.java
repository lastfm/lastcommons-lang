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
    assertEquals("Test3;9;Wed Feb 01 11:32:54 GMT 2012;689;0.9;-", builder.toString());
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
