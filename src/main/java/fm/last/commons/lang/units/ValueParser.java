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

import java.util.Map;

class ValueParser<B> {

  private final Map<Character, B> suffixMap;
  private final String input;
  private B unit;
  private double doubleValue;

  ValueParser(String input, Map<Character, B> suffixMap, B defaultUnit) {
    this.input = input;
    this.suffixMap = suffixMap;
    unit = defaultUnit;
  }

  void parse() {
    if (input == null) {
      throw new IllegalArgumentException("input == null");
    }
    String lowerValue = input.trim().toLowerCase();
    Character suffix = null;
    int suffixPosition = lowerValue.length();
    char[] valueChars = lowerValue.toCharArray();
    for (int i = 0; i < valueChars.length; i++) {
      char c = valueChars[i];
      if (Character.isLetter(c)) {
        suffix = c;
        suffixPosition = i;
        break;
      }
    }
    if (suffix != null) {
      B newUnit = suffixMap.get(suffix);
      if (newUnit != null) {
        unit = newUnit;
      }
    }
    doubleValue = Double.parseDouble(lowerValue.substring(0, suffixPosition));
  }

  B getUnit() {
    return unit;
  }

  double getDoubleValue() {
    return doubleValue;
  }

}
