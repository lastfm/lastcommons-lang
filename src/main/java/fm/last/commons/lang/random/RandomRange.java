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

import java.util.Random;

public final class RandomRange {

  private final static Random random = new java.util.Random();

  private RandomRange() {
  }

  /**
   * Return an int between n1 and n2 n2 must be greater than n1 or n2-n1 must be positive.
   */
  public static int getIntBetween(int n1, int n2) {
    int result = random.nextInt(n2 - n1);
    // plus the offset
    result += n1;
    return result;
  }

  /**
   * Return a double between d1 and d2 d1 and d2 can be any value.
   */
  public static double getDoubleBetween(double d1, double d2) {
    return random.nextDouble() * (d2 - d1) + d1;
  }

  /**
   * Return a float between f1 and f2.
   */
  public static float getFloatBetween(final float f1, final float f2) {
    return random.nextFloat() * (f2 - f1) + f1;
  }

}
