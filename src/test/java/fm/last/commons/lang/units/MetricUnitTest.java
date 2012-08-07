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
package fm.last.commons.lang.units;

import static fm.last.commons.lang.units.MetricUnit.GIGA;
import static fm.last.commons.lang.units.MetricUnit.KILO;
import static fm.last.commons.lang.units.MetricUnit.MEGA;
import static fm.last.commons.lang.units.MetricUnit.TERA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MetricUnitTest {

  @Test
  public void kiloLong() {
    assertThat(KILO.toLong(2L), is(2000L));
  }

  @Test
  public void kiloDouble() {
    assertThat(KILO.toLong(1.5d), is(1500L));
  }

  @Test
  public void megaLong() {
    assertThat(MEGA.toLong(2L), is(2000000L));
  }

  @Test
  public void megaDouble() {
    assertThat(MEGA.toLong(1.3d), is(1300000L));
  }

  @Test
  public void gigaLong() {
    assertThat(GIGA.toLong(2L), is(2000000000L));
  }

  @Test
  public void gigaDouble() {
    assertThat(GIGA.toLong(1.3d), is(1300000000L));
  }

  @Test
  public void teraLong() {
    assertThat(TERA.toLong(2L), is(2000000000000L));
  }

  @Test
  public void teraDouble() {
    assertThat(TERA.toLong(1.9d), is(1900000000000L));
  }

  @Test
  public void suffixKilo() {
    assertThat(KILO.suffix(), is("k"));
  }

  @Test
  public void parseTypical() {
    assertThat(MetricUnit.parse("1.9T"), is(1900000000000L));
  }

  @Test
  public void parseTypicalTrailing() {
    assertThat(MetricUnit.parse("1.9 TB"), is(1900000000000L));
  }

  @Test
  public void parseUnits() {
    assertThat(MetricUnit.parse("1.0001"), is(1L));
  }

  @Test
  public void parseUnknownSuffix() {
    assertThat(MetricUnit.parse("10N"), is(10L));
  }

  @Test
  public void parseUnknownSuffix10000Newtons() {
    assertThat(MetricUnit.parse("10kN"), is(10000L));
  }

  @Test
  public void convertToKilo() {
    assertThat(MetricUnit.KILO.convertTo(MEGA, 1000), is(1.0));
  }

  @Test
  public void convertToUnits() {
    assertThat((float) MetricUnit.UNIT.convertTo(TERA, 1900000000000L), is(1.9f));
  }

}
