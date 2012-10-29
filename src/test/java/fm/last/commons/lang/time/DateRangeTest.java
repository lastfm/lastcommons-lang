package fm.last.commons.lang.time;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

public class DateRangeTest {

  @Test
  public void testGet() {
    DateRange dateRange = new DateRange(new LocalDate(2012, 5, 1), new LocalDate(2012, 5, 12));
    assertEquals(new LocalDate(2012, 5, 1), dateRange.getFirstDate());
    assertEquals(new LocalDate(2012, 5, 12), dateRange.getLastDate());

    DateRange dateRange2 = new DateRange(new LocalDate(2011, 6, 15), new LocalDate(2011, 9, 30));
    assertEquals(new LocalDate(2011, 6, 15), dateRange2.getFirstDate());
    assertEquals(new LocalDate(2011, 9, 30), dateRange2.getLastDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFirstNull() {
    new DateRange(null, new LocalDate(2012, 5, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLastNull() {
    new DateRange(new LocalDate(2012, 5, 1), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFirstAfterLast() {
    new DateRange(new LocalDate(2012, 5, 1), new LocalDate(2012, 4, 30));
  }

  @Test
  public void testEquals() {
    DateRange dateRangeA = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-02-01"));
    assertEquals(dateRangeA, dateRangeA);

    DateRange dateRangeB = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-02-01"));
    assertEquals(dateRangeA, dateRangeB);

    DateRange dateRangeC = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-02-15"));
    DateRange dateRangeD = new DateRange(new LocalDate("2012-01-15"), new LocalDate("2012-02-01"));
    DateRange dateRangeE = new DateRange(new LocalDate("2012-01-15"), new LocalDate("2012-02-15"));

    assertFalse(dateRangeA.equals(dateRangeC));
    assertFalse(dateRangeA.equals(dateRangeD));
    assertFalse(dateRangeA.equals(dateRangeE));

    assertFalse(dateRangeA.equals(null));

    assertFalse(dateRangeA.equals(new Interval(new DateTime("2012-01-01"), new DateTime("2012-01-01"))));
  }

  @Test
  public void testHashcode() {
    Set<DateRange> dateRangeSet = new HashSet<DateRange>();

    dateRangeSet.add(new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-04")));
    dateRangeSet.add(new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-04")));
    dateRangeSet.add(new DateRange(new LocalDate("2012-02-01"), new LocalDate("2012-02-04")));
    dateRangeSet.add(new DateRange(new LocalDate("2012-03-01"), new LocalDate("2012-03-04")));

    assertEquals(3, dateRangeSet.size());
    assertTrue(dateRangeSet.contains(new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-04"))));
    assertTrue(dateRangeSet.contains(new DateRange(new LocalDate("2012-02-01"), new LocalDate("2012-02-04"))));
    assertTrue(dateRangeSet.contains(new DateRange(new LocalDate("2012-03-01"), new LocalDate("2012-03-04"))));
  }

  @Test
  public void testIterator() {
    DateRange dateRange = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-04"));
    List<LocalDate> expected = Arrays.asList(new LocalDate("2012-01-01"), new LocalDate("2012-01-02"),
        new LocalDate("2012-01-03"), new LocalDate("2012-01-04"));

    int i = 0;
    for (LocalDate current : dateRange) {
      assertEquals(expected.get(i++), current);
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveFails() {
    DateRange dateRange = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-04"));
    Iterator<LocalDate> iterator = dateRange.iterator();
    iterator.remove();
  }

  @Test
  public void testNumberOfDays() {
    DateRange dateRange = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-31"));
    assertEquals(31, dateRange.numberOfDays());

    DateRange singleDateRange = new DateRange(new LocalDate("2012-01-01"), new LocalDate("2012-01-01"));
    assertEquals(1, singleDateRange.numberOfDays());
  }

}
