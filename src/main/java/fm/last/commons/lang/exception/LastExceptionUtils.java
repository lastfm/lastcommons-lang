package fm.last.commons.lang.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LastExceptionUtils {

  public static List<Throwable> extractNestedThrowablesOfType(Throwable throwable, Class<?> type) {
    List<Throwable> throwables = new ArrayList<Throwable>();
    throwable = throwable.getCause();
    while (throwable != null) {
      if (type.isAssignableFrom(throwable.getClass())) {
        throwables.add(throwable);
        if (throwable instanceof SQLException && type.equals(SQLException.class)) {
          throwables.addAll(extractNestedSQLExceptions((SQLException) throwable));
        }
      }
      throwable = throwable.getCause();
    }
    return throwables;
  }

  private static List<Throwable> extractNestedSQLExceptions(SQLException exception) {
    List<Throwable> throwables = new ArrayList<Throwable>();
    while (exception.getNextException() != null) {
      exception = exception.getNextException();
      throwables.add(exception);
    }
    return throwables;
  }

}
