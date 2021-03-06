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

import static fm.last.commons.lang.units.BinaryPower.B0;
import static fm.last.commons.lang.units.BinaryPower.B10;
import static fm.last.commons.lang.units.BinaryPower.B20;
import static fm.last.commons.lang.units.BinaryPower.B30;
import static fm.last.commons.lang.units.BinaryPower.B40;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * These provide the JEDEC standardised {@link ByteUnit}s (or binary prefixes) which are not to be confused with the
 * {@link IecByteUnit}s or metric units.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Binary_prefix">Wikipedia - Binary prefix</a>
 * @see <a href="http://en.wikipedia.org/wiki/JEDEC_memory_standards#Unit_prefixes_for_semiconductor_storage_capacity">
 *      Wikipedia - JEDEC standards</a>
 */
public enum JedecByteUnit implements ByteUnit {
  /** 1 */
  BYTES {
    @Override
    public long multiplier() {
      return B0;
    }
  },
  /** 1,024 */
  KILOBYTES {
    @Override
    public long multiplier() {
      return B10;
    }
  },
  /** 1,048,576 */
  MEGABYTES {
    @Override
    public long multiplier() {
      return B20;
    }
  },
  /** 1,073,741,824 */
  GIGABYTES {
    @Override
    public long multiplier() {
      return B30;
    }
  },
  /** 1,099,511,627,776 */
  TERABYTES {
    @Override
    public long multiplier() {
      return B40;
    }
  };

  private static Map<Character, JedecByteUnit> suffixMap;

  static {
    Map<Character, JedecByteUnit> map = new HashMap<Character, JedecByteUnit>();
    for (JedecByteUnit unit : values()) {
      map.put(unit.suffix().toLowerCase().charAt(0), unit);
    }
    suffixMap = Collections.unmodifiableMap(map);
  }

  private final String title;
  private final String suffix;

  private JedecByteUnit() {
    title = name().substring(0, 1) + name().toLowerCase().substring(1, name().length());
    suffix = name().substring(0, 1);
  }

  @Override
  public long toBytes(double multiple) {
    return (long) Math.ceil(multiplier() * multiple);
  }

  @Override
  public long toBytes(long value) {
    return multiplier() * value;
  }

  @Override
  public double convertTo(ByteUnit unit, double value) {
    return (double) toBytes(value) / (double) unit.multiplier();
  }

  @Override
  public String suffix() {
    return suffix;
  }

  @Override
  public String title() {
    return title;
  }

  @Override
  public long multiplier() {
    throw new AbstractMethodError();
  }

  static public long parse(String value) {
    ValueParser<JedecByteUnit> parser = new ValueParser<JedecByteUnit>(value, suffixMap, BYTES);
    parser.parse();
    return parser.getUnit().toBytes(parser.getDoubleValue());
  }

}
