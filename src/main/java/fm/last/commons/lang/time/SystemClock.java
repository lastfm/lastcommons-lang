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

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

class SystemClock extends Clock {

  SystemClock() {
  }

  @Override
  public long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  @Override
  public long nanoTime() {
    return System.nanoTime();
  }

  @Override
  public Calendar getCalendarInstance() {
    return Calendar.getInstance();
  }

  @Override
  public Date newDate() {
    return new Date();
  }

  @Override
  public DateTime newDateTime() {
    return new DateTime();
  }

}
