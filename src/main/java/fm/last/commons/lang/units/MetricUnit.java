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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Do not use this for measuring bytes - use {@link JedecByteUnit} or {@link IecByteUnit} instead.
 */
public enum MetricUnit {
  /** 1 */
  UNIT("") {
    @Override
    public long multiplier() {
      return D0;
    }
  },
  /** 1,000 */
  /** The metric standard says this should be lower case... */
  KILO("k") {
    @Override
    public long multiplier() {
      return D3;
    }
  },
  /** 1,000,000 */
  MEGA {
    @Override
    public long multiplier() {
      return D6;
    }
  },
  /** 1,000,000,000 */
  GIGA {
    @Override
    public long multiplier() {
      return D9;
    }
  },
  /** 1,000,000,000,000 */
  TERA {
    @Override
    public long multiplier() {
      return D12;
    }
  };

  private static Map<Character, MetricUnit> suffixMap;

  static {
    Map<Character, MetricUnit> map = new HashMap<Character, MetricUnit>();
    for (MetricUnit unit : values()) {
      String suffix = unit.suffix().toLowerCase();
      if (suffix.length() > 0) {
        map.put(suffix.charAt(0), unit);
      }
    }
    suffixMap = Collections.unmodifiableMap(map);
  }

  private static final long D0 = 1L;
  private static final long D3 = D0 * 1000L;
  private static final long D6 = D3 * 1000L;
  private static final long D9 = D6 * 1000L;
  private static final long D12 = D9 * 1000L;

  private final String suffix;

  private MetricUnit() {
    suffix = name().substring(0, 1);
  }

  private MetricUnit(String suffix) {
    this.suffix = suffix;
  }

  public long toLong(long value) {
    return multiplier() * value;
  }

  public long toLong(double value) {
    return Math.round(multiplier() * value);
  }

  public String suffix() {
    return suffix;
  }

  public long multiplier() {
    throw new AbstractMethodError();
  }

  public String toString(double value) {
    return Double.toString(toLong(value)) + suffix();
  }

  public String toString(long value) {
    return Long.toString(toLong(value)) + suffix();
  }

  public double convertTo(MetricUnit unit, double value) {
    return (double) toLong(value) / unit.multiplier();
  }

  static public long parse(String value) {
    ValueParser<MetricUnit> parser = new ValueParser<MetricUnit>(value, suffixMap, UNIT);
    parser.parse();
    return parser.getUnit().toLong(parser.getDoubleValue());
  }

}