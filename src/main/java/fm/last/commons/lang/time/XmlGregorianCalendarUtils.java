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

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Utility functions for working with DateTime objects.
 */
public final class XmlGregorianCalendarUtils {

  /**
   * Private constructor to prevent instantiation.
   */
  private XmlGregorianCalendarUtils() {
  }

  /**
   * Converts from an XMLGregorianCalendar to a LocalDateTime i.e. represents a date and time without timezone
   * inform.ation
   * 
   * @param calendar XMLGregorianCalendar object.
   * @return The calendar converted to a LocalDateTime.
   */
  public static LocalDateTime convertToLocalDateTime(XMLGregorianCalendar calendar) {
    if (calendar == null) {
      return null;
    }
    int year = calendar.getYear() > 0 ? calendar.getYear() : 0;
    int hour = calendar.getHour() > 0 ? calendar.getHour() : 0;
    int minute = calendar.getMinute() > 0 ? calendar.getMinute() : 0;
    int second = calendar.getSecond() > 0 ? calendar.getSecond() : 0;
    int millisecond = calendar.getMillisecond() > 0 ? calendar.getMillisecond() : 0;

    return new LocalDateTime(year, calendar.getMonth(), calendar.getDay(), hour, minute, second, millisecond);
  }

  public static DateTime convertToDateTime(XMLGregorianCalendar calendar) {
    if (calendar == null) {
      return null;
    }
    // for now we just delegate to version which doesn't deal with time zones, this method should be amended to take TZ
    // into consideration if needed
    return convertToLocalDateTime(calendar).toDateTime();
  }

  /**
   * Converts from an XMLGregorianCalendar to a LocalTime i.e. represents a time without timezone or date information.
   * 
   * @param calendar XMLGregorianCalendar object.
   * @return The calendar converted to a LocalTime.
   */
  public static LocalTime convertToLocalTime(XMLGregorianCalendar calendar) {
    if (calendar == null) {
      return null;
    }
    int hour = calendar.getHour() > 0 ? calendar.getHour() : 0;
    int minute = calendar.getMinute() > 0 ? calendar.getMinute() : 0;
    int second = calendar.getSecond() > 0 ? calendar.getSecond() : 0;
    int millisecond = calendar.getMillisecond() > 0 ? calendar.getMillisecond() : 0;

    return new LocalTime(hour, minute, second, millisecond);
  }

}
