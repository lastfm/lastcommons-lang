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

import static fm.last.commons.lang.units.IecByteUnit.GIBIBYTES;
import static fm.last.commons.lang.units.IecByteUnit.KIBIBYTES;
import static fm.last.commons.lang.units.IecByteUnit.MEBIBYTES;
import static fm.last.commons.lang.units.IecByteUnit.TEBIBYTES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IecByteUnitTest {

  @Test
  public void kibiLong() {
    assertThat(KIBIBYTES.toBytes(2L), is(2048L));
  }

  @Test
  public void kibiDouble() {
    assertThat(KIBIBYTES.toBytes(1.5d), is(1536L));
  }

  @Test
  public void mebiLong() {
    assertThat(MEBIBYTES.toBytes(2L), is(2097152L));
  }

  @Test
  public void mebiDouble() {
    assertThat(MEBIBYTES.toBytes(1.3d), is(1363149L));
  }

  @Test
  public void gibiLong() {
    assertThat(GIBIBYTES.toBytes(2L), is(2147483648L));
  }

  @Test
  public void gibiDouble() {
    assertThat(GIBIBYTES.toBytes(1.3d), is(1395864372L));
  }

  @Test
  public void tebiLong() {
    assertThat(TEBIBYTES.toBytes(2L), is(2199023255552L));
  }

  @Test
  public void tebiDouble() {
    assertThat(TEBIBYTES.toBytes(1.9d), is(2089072092775L));
  }

  @Test
  public void suffixKibi() {
    assertThat(KIBIBYTES.suffix(), is("Ki"));
  }

  @Test
  public void parseTypical() {
    assertThat(IecByteUnit.parse("1.9T"), is(2089072092775L));
  }

  @Test
  public void parseUnits() {
    assertThat(IecByteUnit.parse("1.0001"), is(2L));
  }

  @Test
  public void parseUnknownSuffix() {
    assertThat(IecByteUnit.parse("10N"), is(10L));
  }

  @Test
  public void parseTypicalTrailing() {
    assertThat(IecByteUnit.parse("1.9 TiB"), is(2089072092775L));
  }

  @Test
  public void convertToKibi() {
    assertThat(IecByteUnit.KIBIBYTES.convertTo(MEBIBYTES, 1024), is(1.0));
  }

  @Test
  public void convertToBytes() {
    assertThat((float) IecByteUnit.BYTES.convertTo(TEBIBYTES, 2089072092775L), is(1.9f));
  }

}
