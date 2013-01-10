/*
 * Copyright 2013 Last.fm
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
package fm.last.commons.lang.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for dealing with exceptions.
 */
public class LastExceptionUtils {

  /**
   * Extracts a List of Throwable objects that are assignable from the passed type by "walking" the passed Throwable and
   * checking all nested exceptions.
   * 
   * @param throwable The Throwable to extract child exceptions from.
   * @param type The matching type of nested Throwable to extract.
   * @return A List of matching nested Throwables.
   */
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
