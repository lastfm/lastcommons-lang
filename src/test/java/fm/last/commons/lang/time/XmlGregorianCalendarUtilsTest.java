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

import static org.junit.Assert.assertEquals;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

public class XmlGregorianCalendarUtilsTest {

  @Test
  public void convertNullToLocalDateTime() {
    assertEquals(null, XmlGregorianCalendarUtils.convertToLocalDateTime(null));
  }

  @Test
  public void convertNullToLocalTime() {
    assertEquals(null, XmlGregorianCalendarUtils.convertToLocalTime(null));
  }

  @Test
  public void testConvertToLocalTime() throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    calendar.setYear(1976);
    calendar.setMonth(3);
    calendar.setDay(29);
    calendar.setHour(15);
    calendar.setMinute(33);
    calendar.setSecond(11);
    calendar.setMillisecond(101);
    DateTime dateTime = XmlGregorianCalendarUtils.convertToDateTime(calendar);
    assertEquals(1976, dateTime.getYear());
    assertEquals(3, dateTime.getMonthOfYear());
    assertEquals(29, dateTime.getDayOfMonth());
    assertEquals(15, dateTime.getHourOfDay());
    assertEquals(33, dateTime.getMinuteOfHour());
    assertEquals(11, dateTime.getSecondOfMinute());
    assertEquals(101, dateTime.getMillisOfSecond());
  }

  @Test
  public void testConvertToDateTimeWithNull() throws DatatypeConfigurationException {
    assertEquals(null, XmlGregorianCalendarUtils.convertToDateTime(null));
  }

  @Test
  public void testConvertToLocal() throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    calendar.setHour(3);
    calendar.setMinute(33);
    calendar.setSecond(11);
    calendar.setMillisecond(101);
    LocalTime localTime = XmlGregorianCalendarUtils.convertToLocalTime(calendar);
    assertEquals(3, localTime.getHourOfDay());
    assertEquals(33, localTime.getMinuteOfHour());
    assertEquals(11, localTime.getSecondOfMinute());
    assertEquals(101, localTime.getMillisOfSecond());
  }

  @Test
  public void testConvertToLocalTimeWithNull() throws DatatypeConfigurationException {
    assertEquals(null, XmlGregorianCalendarUtils.convertToLocalTime(null));
  }

  @Test
  public void testConvertToLocalTimeAllZeros() throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    calendar.setHour(0);
    calendar.setMinute(0);
    calendar.setSecond(0);
    calendar.setMillisecond(0);
    LocalTime localTime = XmlGregorianCalendarUtils.convertToLocalTime(calendar);
    assertEquals(0, localTime.getHourOfDay());
    assertEquals(0, localTime.getMinuteOfHour());
    assertEquals(0, localTime.getSecondOfMinute());
    assertEquals(0, localTime.getMillisOfSecond());
  }

  @Test
  public void testConvertZeroFields() throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    // month and day cannot be set to zero in xerces impl of XMLGregorianCalendar
    calendar.setMonth(3);
    calendar.setDay(29);
    // set all other fields to zero
    calendar.setYear(0);
    calendar.setMinute(0);
    calendar.setSecond(0);
    calendar.setMillisecond(0);
    DateTime dateTime = XmlGregorianCalendarUtils.convertToDateTime(calendar);
    assertEquals(0, dateTime.getYear());
    assertEquals(3, dateTime.getMonthOfYear());
    assertEquals(29, dateTime.getDayOfMonth());
    assertEquals(0, dateTime.getMinuteOfHour());
    assertEquals(0, dateTime.getSecondOfMinute());
    assertEquals(0, dateTime.getMillisOfSecond());
  }

  @Test
  public void testConvertNegativeYear() throws DatatypeConfigurationException {
    XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
    // xerces impl of XMLGregorianCalendar only allows negative values for year
    calendar.setYear(-1);
    calendar.setMonth(3);
    calendar.setDay(29);
    calendar.setMinute(33);
    calendar.setSecond(11);
    calendar.setMillisecond(101);
    DateTime dateTime = XmlGregorianCalendarUtils.convertToDateTime(calendar);
    assertEquals(0, dateTime.getYear());
    assertEquals(3, dateTime.getMonthOfYear());
    assertEquals(29, dateTime.getDayOfMonth());
    assertEquals(33, dateTime.getMinuteOfHour());
    assertEquals(11, dateTime.getSecondOfMinute());
    assertEquals(101, dateTime.getMillisOfSecond());
  }

}
