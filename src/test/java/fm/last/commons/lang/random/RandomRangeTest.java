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
package fm.last.commons.lang.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RandomRangeTest {

  @Test
  public void getIntBetweenWithZero() {
    assertEquals(0, RandomRange.getIntBetween(0, 1));
  }

  @Test
  public void getIntBetweenWithANeg() {
    int num = RandomRange.getIntBetween(-1, 20);
    assertTrue("Unexpected value " + num, num <= 20);
    assertTrue("Unexpected value " + num, num >= -1);
  }

  @Test
  public void getDoubleBetween() {
    double num = RandomRange.getDoubleBetween(1, 10);
    assertTrue(num >= 1);
    assertTrue(num <= 10);
  }

  @Test
  public void getDoubleBetweenWithANeg() {
    double num = RandomRange.getDoubleBetween(-1, 10);
    assertTrue(num >= -1);
    assertTrue(num <= 10);
  }

  @Test
  public void getDoubleBetweenWithTwoNegs() {
    double num = RandomRange.getDoubleBetween(-1, -10);
    assertTrue(num <= -1);
    assertTrue(num >= -10);
  }

  @Test
  public void getFloatBetween() {
    float num = RandomRange.getFloatBetween(1.5f, 10.5f);
    assertTrue(num >= 1.5);
    assertTrue(num <= 10.5);
  }

  @Test
  public void getFloatBetweenWithANeg() {
    float num = RandomRange.getFloatBetween(-1.5f, 10.5f);
    assertTrue(num >= -1.5);
    assertTrue(num <= 10.5);
  }

  @Test
  public void getFloatBetweenWithTwoNeg() {
    float num = RandomRange.getFloatBetween(-1.5f, -10.5f);
    assertTrue(num <= -1.5);
    assertTrue(num >= -10.5);
  }
}
