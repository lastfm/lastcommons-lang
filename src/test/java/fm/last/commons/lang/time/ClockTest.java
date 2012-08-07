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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClockTest {

  private final Clock clock = Clock.getInstance();

  @Test
  public void currentTimeMillis() {
    assertTrue(clock.currentTimeMillis() != 0);
  }

  @Test
  public void nanoTime() {
    assertTrue(clock.nanoTime() != 0);
  }

  @Test
  public void getCalendarInstance() {
    assertNotNull(clock.getCalendarInstance());
  }

  @Test
  public void newDate() {
    assertNotNull(clock.newDate());
  }

  @Test
  public void newDateTime() {
    assertNotNull(clock.newDateTime());
  }
}
