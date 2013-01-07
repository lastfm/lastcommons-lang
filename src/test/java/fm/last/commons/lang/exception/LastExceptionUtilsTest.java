package fm.last.commons.lang.exception;

import static org.junit.Assert.assertEquals;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

public class LastExceptionUtilsTest {

  @Test
  public void extractOneNestedThrowable() {
    IllegalArgumentException nested2 = new IllegalArgumentException();
    IllegalStateException nested1 = new IllegalStateException(nested2);
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable,
        IllegalArgumentException.class);
    assertEquals(1, throwables.size());
    assertEquals(nested2, throwables.get(0));

    throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable, IllegalStateException.class);
    assertEquals(1, throwables.size());
    assertEquals(nested1, throwables.get(0));
  }

  @Test
  public void extractMultipleNestedThrowables() {
    IllegalArgumentException nested6 = new IllegalArgumentException();
    IllegalStateException nested5 = new IllegalStateException(nested6);
    IllegalArgumentException nested4 = new IllegalArgumentException(nested5);
    IllegalArgumentException nested3 = new IllegalArgumentException(nested4);
    IllegalArgumentException nested2 = new IllegalArgumentException(nested3);
    IllegalArgumentException nested1 = new IllegalArgumentException(nested2);
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable,
        IllegalArgumentException.class);
    assertEquals(5, throwables.size());
    assertEquals(nested1, throwables.get(0));
    assertEquals(nested2, throwables.get(1));
    assertEquals(nested3, throwables.get(2));
    assertEquals(nested4, throwables.get(3));
    assertEquals(nested6, throwables.get(4));

    throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable, IllegalStateException.class);
    assertEquals(1, throwables.size());
    assertEquals(nested5, throwables.get(0));
  }

  @Test
  public void extractNoMatchingNestedThrowable() {
    IllegalArgumentException nested2 = new IllegalArgumentException();
    IllegalStateException nested1 = new IllegalStateException(nested2);
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable,
        ArrayIndexOutOfBoundsException.class);
    assertEquals(0, throwables.size());
  }

  @Test
  public void extractNestedSQLException() {
    SQLException nested1 = new SQLException();
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable, SQLException.class);
    assertEquals(1, throwables.size());
    assertEquals(nested1, throwables.get(0));
  }

  @Test
  public void extractNestedSQLExceptionWithNextException() {
    SQLException nested2 = new SQLException("nested2");
    SQLException nested1 = new SQLException("nested1");
    nested1.setNextException(nested2);
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable, SQLException.class);
    assertEquals(2, throwables.size());
    assertEquals(nested1, throwables.get(0));
    assertEquals(nested2, throwables.get(1));
  }

  @Test
  public void extractNestedSubTypeSQLException() {
    SQLException nested2 = new SQLException("nested2");
    BatchUpdateException nested1 = new BatchUpdateException();
    nested1.setNextException(nested2);
    Throwable throwable = new Throwable(nested1);

    List<Throwable> throwables = LastExceptionUtils.extractNestedThrowablesOfType(throwable, SQLException.class);
    assertEquals(2, throwables.size());
    assertEquals(nested1, throwables.get(0));
    assertEquals(nested2, throwables.get(1));
  }

}
