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
package fm.last.commons.lang.time;

import java.util.Iterator;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Represents an Iterable range of LocalDates. This is a discrete set of LocalDates, which makes it distinct from the
 * joda-time Interval class, which represents a continuous interval between two precise Instants.
 */
public class DateRange implements Iterable<LocalDate> {

  private final LocalDate firstDate;
  private final LocalDate lastDate;

  /**
   * Create a new DateRange from firstDate to lastDate (inclusive)
   * 
   * @param firstDate - First LocalDate in the DateRange
   * @param lastDate - Last LocalDate in the DateRange
   */
  public DateRange(LocalDate firstDate, LocalDate lastDate) {
    validateDates(firstDate, lastDate);
    this.firstDate = firstDate;
    this.lastDate = lastDate;
  }

  private void validateDates(LocalDate firstDate, LocalDate lastDate) {
    if (firstDate == null) {
      throw new IllegalArgumentException("First date cannot be null");
    }
    if (lastDate == null) {
      throw new IllegalArgumentException("Last date cannot be null");
    }
    if (firstDate.isAfter(lastDate)) {
      throw new IllegalArgumentException("First date cannot be after last date");
    }
  }

  @Override
  public Iterator<LocalDate> iterator() {
    return new DateRangeIterator();
  }

  public LocalDate getFirstDate() {
    return firstDate;
  }

  public LocalDate getLastDate() {
    return lastDate;
  }

  /**
   * @return The number of days (inclusive) between the first and last dates in the range
   */
  public int numberOfDays() {
    Period period = new Period(firstDate, lastDate, PeriodType.days());
    return period.getDays() + 1; // Add one to be inclusive of lastDate as well
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + firstDate.hashCode();
    result = prime * result + lastDate.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof DateRange)) {
      return false;
    }
    DateRange other = (DateRange) obj;
    if (!firstDate.equals(other.firstDate)) {
      return false;
    }
    if (!lastDate.equals(other.lastDate)) {
      return false;
    }
    return true;
  }

  private class DateRangeIterator implements Iterator<LocalDate> {
    private LocalDate currentDate;

    private DateRangeIterator() {
      currentDate = firstDate;
    }

    @Override
    public boolean hasNext() {
      return !currentDate.isAfter(lastDate);
    }

    @Override
    public LocalDate next() {
      LocalDate current = currentDate;
      currentDate = currentDate.plusDays(1);
      return current;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Cannot remove a LocalDate from a DateRange");
    }

  }
}
